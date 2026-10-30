#!/usr/bin/env bash
# Toolbox policy is additive to capy's own execution/routing hooks.
set -Eeuo pipefail

fail_closed() {
  trap - ERR
  printf '%s\n' 'kk guard: failure — Codex MCP adapter could not run; nothing was executed; repair the hook' >&2
  exit 2
}
trap fail_closed ERR HUP INT TERM
exec 3<&0 || fail_closed

SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)
REPO_ROOT=$(cd -- "$SCRIPT_DIR/../.." && pwd)
export KK_GUARD_PROJECT_ROOT="$REPO_ROOT"
if bash "$SCRIPT_DIR/guard/guard.sh" <&3; then
  exit 0
else
  status=$?
  # Exit 2 is a guard denial, already explained on stdout and stderr.
  if [[ "$status" == 2 ]]; then
    exit 2
  fi
  fail_closed
fi
