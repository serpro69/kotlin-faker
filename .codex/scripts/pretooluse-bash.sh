#!/usr/bin/env bash
# PreToolUse hook for shell commands — file-path denylist and capy HTTP routing.
# Reads tool_input.command from stdin JSON. Emits permissionDecision: "deny" on
# match; exits 0 silently on pass-through.

set -euo pipefail

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

# --- File-path denylist (ported from klaude-plugin/scripts/validate-bash.sh) ---

FORBIDDEN_PATTERNS=(
  "\.env"
  "\.ansible\/"
  "\.terraform\/"
  "build\/"
  "dist\/"
  "node_modules\/"
  "target\/"
  "__pycache__\/"
  "\.git\/"
  "venv\/"
  "\.pyc$"
  "\.csv$"
  "\.log$"
)

for pattern in "${FORBIDDEN_PATTERNS[@]}"; do
  if printf '%s' "$COMMAND" | grep -qE "$pattern"; then
    deny "Access to '$pattern' is blocked by security policy"
  fi
done

# --- Capy HTTP routing (block curl/wget and inline HTTP patterns) ---

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
