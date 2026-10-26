#!/usr/bin/env bash
# PreToolUse hook for shell commands — shared permission guard (always) and legacy
# capy HTTP routing (only while capy's own Codex hooks are not registered).
# Reads tool_input.command from stdin JSON. Emits permissionDecision: "deny" on
# match; exits 2 with stderr on denial and 0 on pass-through.

set -Eeuo pipefail

fail_closed() {
  trap - ERR
  printf '%s\n' 'kk guard: failure — Codex Bash adapter could not run; nothing was executed; repair the hook' >&2
  exit 2
}
trap fail_closed ERR HUP INT TERM

# Preserve the payload bytes, including malformed JSON containing raw NULs.
# Save stdin before command substitution can reuse a closed fd 0.
exec 3<&0 || fail_closed

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"

scratch=$(mktemp -d "${TMPDIR:-/tmp}/kk-codex-bash.XXXXXXXX")
trap 'rm -rf -- "$scratch"' EXIT
cat <&3 >"$scratch/payload" || fail_closed
exec 3<&-

export KK_GUARD_PROJECT_ROOT="$REPO_ROOT"
if bash "$SCRIPT_DIR/guard/guard.sh" <"$scratch/payload" >"$scratch/decision"; then
  :
else
  status=$?
  if [[ "$status" == 2 ]]; then
    cat "$scratch/decision"
    exit 2
  fi
  fail_closed
fi

COMMAND=$(jq -r '.tool_input.command // empty' "$scratch/payload")

if [ -z "$COMMAND" ]; then
  cat "$scratch/decision"
  exit 0
fi

deny() {
  printf '%s\n' "$1" >&2
  jq -n --arg reason "$1" --slurpfile warning "$scratch/decision" \
    '($warning[0] // {}) + {hookSpecificOutput: {hookEventName: "PreToolUse", permissionDecision: "deny", permissionDecisionReason: $reason}}'
  exit 2
}

# Capy >= v0.17.1 registers its own Codex hooks through
# `capy setup --platform codex`; every command it registers invokes
# `capy codex-launch`. The check is per event and re-evaluated on every call:
# remove capy's entries from hooks.json and the legacy routing returns.
# session-start.sh applies the same check to SessionStart.
capy_hook_registered() {
  local hooks_json="${REPO_ROOT}/.codex/hooks.json"
  [[ -f "$hooks_json" ]] && jq -e --arg event "$1" \
    '[.hooks[$event][]?.hooks[]?.command // empty] | any(contains("capy codex-launch"))' \
    "$hooks_json" >/dev/null 2>&1
}

# --- Legacy capy HTTP routing (block curl/wget and inline HTTP patterns) ---
#
# Capy's own PreToolUse hook applies an availability-aware policy (advisory
# guidance, evidence-based redirects, a silent-download exception) that this
# unconditional deny would override. When that hook is registered, stop here:
# the shared guard above has already run and always applies.

if capy_hook_registered PreToolUse; then
  cat "$scratch/decision"
  exit 0
fi

if printf '%s' "$COMMAND" | grep -qE '\bcurl\b'; then
  deny "curl is blocked. Use capy_fetch_and_index(url, source) or capy_execute() instead"
fi

if printf '%s' "$COMMAND" | grep -qE '\bwget\b'; then
  deny "wget is blocked. Use capy_fetch_and_index(url, source) or capy_execute() instead"
fi

INLINE_HTTP_PATTERNS=(
  "fetch('http"
  "requests.get("
  "requests.post("
  "http.get("
  "http.request("
)

for pattern in "${INLINE_HTTP_PATTERNS[@]}"; do
  if printf '%s' "$COMMAND" | grep -qF "$pattern"; then
    deny "Inline HTTP ('$pattern') is blocked. Use capy_execute(language, code) instead"
  fi
done

cat "$scratch/decision"
exit 0
