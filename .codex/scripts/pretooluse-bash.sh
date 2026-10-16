#!/usr/bin/env bash
# PreToolUse hook for shell commands — file-path denylist (always) and legacy
# capy HTTP routing (only while capy's own Codex hooks are not registered).
# Reads tool_input.command from stdin JSON. Emits permissionDecision: "deny" on
# match; exits 0 silently on pass-through.

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"

INPUT=$(cat)
COMMAND=$(printf '%s' "$INPUT" | jq -r '.tool_input.command // empty')

if [ -z "$COMMAND" ]; then
  exit 0
fi

deny() {
  jq -n --arg reason "$1" \
    '{hookSpecificOutput: {hookEventName: "PreToolUse", permissionDecision: "deny", permissionDecisionReason: $reason}}'
  exit 0
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

# --- File-path denylist (ported from klaude-plugin/scripts/validate-bash.sh) ---

FORBIDDEN_PATTERNS=(
  "\.env"
  "\.ansible\/"
  "\.terraform\/"
  "build\/"
  "dist\/"
  "target\/"
  "__pycache__\/"
  "\.git\/"
  "venv\/"
  "\.pyc$"
  "\.csv$"
)

for pattern in "${FORBIDDEN_PATTERNS[@]}"; do
  if printf '%s' "$COMMAND" | grep -qE "$pattern"; then
    deny "Access to '$pattern' is blocked by security policy"
  fi
done

# --- Legacy capy HTTP routing (block curl/wget and inline HTTP patterns) ---
#
# Capy's own PreToolUse hook applies an availability-aware policy (advisory
# guidance, evidence-based redirects, a silent-download exception) that this
# unconditional deny would override. When that hook is registered, stop here:
# the file-path denylist above has already run and always applies.

if capy_hook_registered PreToolUse; then
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

exit 0
