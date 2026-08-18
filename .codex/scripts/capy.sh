#!/usr/bin/env bash

# Wrapper that locates and runs the capy binary.
# Used as a Claude Code hook — must always exit 0 to avoid phantom hook errors.
# See: https://github.com/serpro69/claude-toolbox/issues/57

set -uo pipefail

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
