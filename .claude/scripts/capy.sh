#!/usr/bin/env bash

# Go resolves knowledge credentials; vault credentials stay environment-only.
set -uo pipefail

p=""
for candidate in "$(command -v capy 2>/dev/null || true)" "$HOME/.local/bin/capy" "/opt/homebrew/bin/capy" "/usr/local/bin/capy" "$HOME/go/bin/capy" "capy"; do
  if [ -n "$candidate" ] && [ -f "$candidate" ] && [ -x "$candidate" ]; then
    p="$candidate"
    break
  fi
done
if [ -n "$p" ]; then
  if [ "${1:-}" = "hook" ]; then
    "$p" "$@" || true
    exit 0
  fi
  exec "$p" "$@"
fi

if [ "${1:-}" = "hook" ]; then
  jq -n --arg reason "capy binary not found" \
    '{hookSpecificOutput: {hookEventName: "PreToolUse", permissionDecision: "deny", permissionDecisionReason: $reason}}'
  exit 0
fi

echo "capy: binary not found (searched PATH, \$HOME/.local/bin, /opt/homebrew/bin, /usr/local/bin, \$HOME/go/bin)" >&2
exit 127
