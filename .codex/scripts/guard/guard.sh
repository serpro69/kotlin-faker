#!/bin/bash
# Fail-closed entry point, adapted from safe-claude-code's hooks/guard.sh.
# Dispatch belongs to guard.py: even a read without config must reach it.
# Python runs isolated from PYTHON*, site packages and startup customizations.
# Absolute candidates come first; PATH is the final fallback for pyenv/uv.
set -Eeuo pipefail
set -f

fail_closed() {
    trap - ERR
    set +e
    # Causes are controlled strings, never payloads, paths or engine output;
    # this JSON remains valid even when Python and jq cannot run.
    local reason="kk guard: failure — the guard could not evaluate this call. Way through: the guard could not run ($1); nothing was executed; fix the guard or run the command yourself"
    printf '%s\n' "$reason" >&2
    printf '{"hookSpecificOutput":{"hookEventName":"PreToolUse","permissionDecision":"deny","permissionDecisionReason":"%s"}}\n' "$reason"
    exit 2
}

trap 'fail_closed "unexpected wrapper failure"' ERR
trap 'fail_closed "wrapper interrupted"' HUP INT TERM

# Save stdin before command substitutions allocate pipes: when fd 0 is closed,
# Bash can reuse it for a substitution's pipe and cat would wait on itself.
exec 3<&0 || fail_closed "cannot read hook payload"

guard_source=${BASH_SOURCE[0]}
case "$guard_source" in
    */*) guard_parent=${guard_source%/*}; guard_parent=${guard_parent:-/} ;;
    *) guard_parent=. ;;
esac
GUARD_DIR=$(cd -- "$guard_parent" && pwd)
GUARD_PY="$GUARD_DIR/guard.py"
[[ -f "$GUARD_PY" ]] || fail_closed "guard.py is missing"
GUARD_RUNNER="$GUARD_DIR/run-guard.py"
[[ -f "$GUARD_RUNNER" ]] || fail_closed "run-guard.py is missing"

# A private directory keeps stdout/stderr separate until the exit status and
# output contract have been checked. Never forward a crashed engine's output.
scratch=$(mktemp -d "${TMPDIR:-/tmp}/kk-guard.XXXXXXXX")
trap 'rm -rf -- "$scratch"' EXIT

# Buffer once without interpreting bytes. Unlike a Bash variable, a private
# file preserves NULs too, so malformed JSON stays malformed in the engine.
cat <&3 >"$scratch/payload" || fail_closed "cannot read hook payload"
exec 3<&-

# Override is a whitespace-separated list of executable names/paths, not shell
# code. An explicitly empty list means no candidates (useful in failure tests).
default_interpreters='/usr/bin/python3 /opt/homebrew/bin/python3 /usr/local/bin/python3 python3'
case "${OSTYPE:-}" in
    darwin*)
        # Prefer native Homebrew Python over the slower Command Line Tools
        # interpreter, while retaining the system and PATH fallbacks.
        default_interpreters='/opt/homebrew/bin/python3 /usr/local/bin/python3 /usr/bin/python3 python3'
        ;;
esac
interpreters=${KK_GUARD_INTERPRETERS-"$default_interpreters"}
IFS=$' \t\n'
for candidate in $interpreters; do
    command -v -- "$candidate" >/dev/null 2>&1 || continue
    # The engine uses only the standard library; site initialization is unused.
    if { "$candidate" -I -S "$GUARD_RUNNER" <"$scratch/payload"; } >"$scratch/out" 2>"$scratch/err"; then
        rc=0
    else
        rc=$?
    fi
    case "$rc" in
        0)
            # Silence is the normal allow. A config warning may contain only
            # systemMessage; an allow decision is never part of the contract.
            if [[ -s "$scratch/out" ]]; then
                jq -e -s 'length == 1 and (.[0] | type == "object" and
                    keys == ["systemMessage"] and (.systemMessage | type == "string"))' \
                    "$scratch/out" >/dev/null 2>&1 || fail_closed "malformed engine output"
            fi
            ;;
        2)
            jq -e -s --rawfile diagnostics "$scratch/err" '
                length == 1 and (.[0].hookSpecificOutput |
                    .hookEventName == "PreToolUse" and .permissionDecision == "deny" and
                    (.permissionDecisionReason | type == "string" and test("\\S"))) and
                ($diagnostics | test("\\S"))' "$scratch/out" >/dev/null 2>&1 ||
                fail_closed "malformed engine denial or missing stderr reason"
            ;;
        *)
            # A macOS interpreter shim can exist without working. Retry only
            # when the interpreter itself fails; an engine crash must block.
            if "$candidate" -I -S -c 'import sys' >/dev/null 2>&1; then
                fail_closed "guard.py exited unexpectedly with status $rc"
            fi
            continue
            ;;
    esac
    # Missing/unreadable captures must still fail closed. Empty captures need
    # no subprocess, which is the ordinary allow path for both streams.
    [[ -f "$scratch/err" && -r "$scratch/err" && -f "$scratch/out" && -r "$scratch/out" ]] ||
        fail_closed "cannot read engine output"
    if [[ -s "$scratch/err" ]]; then cat "$scratch/err" >&2; fi
    if [[ -s "$scratch/out" ]]; then cat "$scratch/out"; fi
    exit "$rc"
done
fail_closed "no working Python interpreter; install or repair Python 3.8 or newer"
