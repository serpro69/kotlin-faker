#!/usr/bin/env bash

# Wrapper that locates and runs the capy binary.
# Used as a Claude Code hook — hook events must always exit 0 to avoid phantom hook
# errors. Every other subcommand execs the binary so its real exit code propagates
# (a pre-commit checkpoint must be able to fail the commit).
# See: https://github.com/serpro69/claude-toolbox/issues/57

set -uo pipefail

# Go resolves knowledge credentials for the selected project. Vault credentials
# must already be present in the launch environment.

for p in "$(command -v capy 2>/dev/null || true)" "$HOME/.local/bin/capy" "/opt/homebrew/bin/capy" "/usr/local/bin/capy" "$HOME/go/bin/capy" "capy"; do
  if [ -n "$p" ] && [ -x "$p" ]; then
    # Hook events must always exit 0; every other subcommand execs the binary so
    # its real exit code reaches the caller.
    if [ "${1:-}" = "hook" ]; then
      "$p" "$@" || true
      exit 0
    fi
    exec "$p" "$@"
  fi
done

# capy not found. Hook events still exit 0 (deny the tool use); every other
# subcommand fails loud so callers — the pre-commit hook especially — can react.
if [ "${1:-}" = "hook" ]; then
  jq -n --arg reason "capy binary not found" \
  	'{hookSpecificOutput: {hookEventName: "PreToolUse", permissionDecision: "deny", permissionDecisionReason: $reason}}'
  exit 0
fi

echo "capy: binary not found (searched PATH, \$HOME/.local/bin, /opt/homebrew/bin, /usr/local/bin, \$HOME/go/bin)" >&2
exit 127
