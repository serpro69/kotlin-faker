#!/usr/bin/env bash

# Wrapper that locates and runs the capy binary.
# Used as a Claude Code hook — must always exit 0 to avoid phantom hook errors.
# See: https://github.com/serpro69/claude-toolbox/issues/57

set -uo pipefail

# The MCP key (CAPY_DB_KEY) is env-only, and .env is gitignored. A shell opened
# directly inside a linked git worktree never sources it — the gitignored .env was
# never created there and no dotenv plugin (direnv, omz-dotenv) reaches the main
# worktree — so capy aborts and Claude Code shows only an opaque
# "MCP error -32000: Connection closed". Recover the key here (the one place that
# always runs for `serve`), then fail loud if recovery is impossible.
# Only `serve` gets this treatment — hook events must still fall through and exit 0.
if [ "${1:-}" = "serve" ] && [ -z "${CAPY_DB_KEY:-}" ]; then
  # git-common-dir resolves to the MAIN worktree's .git from any linked worktree
  # (and to .git in a normal checkout); its parent owns the shared, gitignored .env.
  if common_dir="$(git rev-parse --path-format=absolute --git-common-dir 2>/dev/null)"; then
    main_env="$(dirname "$common_dir")/.env"
    if [ -f "$main_env" ]; then
      set -a
      # shellcheck disable=SC1090
      . "$main_env"
      set +a
      echo "capy serve: no CAPY_DB_KEY in this shell; sourced $main_env" >&2
    fi
  fi
fi

if [ "${1:-}" = "serve" ] && [ -z "${CAPY_DB_KEY:-}" ]; then
  cat >&2 <<'EOF'
capy serve: CAPY_DB_KEY is unset and could not be recovered.

The usual cause: you opened a shell directly inside a linked git worktree, whose
gitignored .env was never created there, so no dotenv plugin sourced it. This
wrapper tried the main worktree's .env as a fallback but found no key.

Fix: ensure the main worktree has a .env defining CAPY_DB_KEY, or export the key
in this shell before starting capy.
EOF
  exit 1
fi

for p in "$(command -v capy 2>/dev/null || true)" "$HOME/.local/bin/capy" "/opt/homebrew/bin/capy" "/usr/local/bin/capy" "$HOME/go/bin/capy" "capy"; do
  if [ -n "$p" ] && [ -x "$p" ]; then
    "$p" "$@" || true
    exit 0
  fi
done

# capy not found — deny tool use
jq -n --arg reason "capy binary not found" \
	'{hookSpecificOutput: {hookEventName: "PreToolUse", permissionDecision: "deny", permissionDecisionReason: $reason}}'
exit 0
