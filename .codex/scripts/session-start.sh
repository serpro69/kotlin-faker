#!/usr/bin/env bash
# SessionStart hook for Codex — injects provider context into the session.

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"

read -r -d '' CONTEXT <<'CONTEXT_EOF' || true
Provider: Codex (OpenAI).

# Tool-Name Mapping

Skills reference Claude Code tool names. Translate these to codex native tool names:
- Read
- Write
- Edit
- Bash
- Grep
- Glob
- WebSearch
- WebFetch
- Agent/Task
- Skill

## Tool Usage

- ALWAYS use native tools for file writes/edits instead of ad-hoc scripts via python or similar commands
CONTEXT_EOF

# Aggregate all .claude/CLAUDE.<name>.md instruction files (CLAUDE.extra.md and
# any custom extras). Deliberately excludes .claude/toolbox/CLAUDE.md — that file
# is Claude-harness-specific (plugin-root override does not apply to Codex).
# NOTE: it's important that all additional instruction files start with H1 root header for consistency
AGENTS_EXTRA_MD=""
for extra_md in "${REPO_ROOT}"/.claude/CLAUDE.*.md; do
  [[ -f "$extra_md" ]] || continue
  AGENTS_EXTRA_MD="${AGENTS_EXTRA_MD}$(cat "$extra_md")"$'\n\n'
done

# Capy guidance. Capy >= v0.17.1 registers its own Codex hooks through
# `capy setup --platform codex`; every command it registers invokes
# `capy codex-launch`. When such a command is registered for SessionStart, capy
# delivers its routing guidance itself and this hook must not inject a second,
# older copy from .capy/AGENTS.md. The check is per event and re-evaluated on
# every session start: remove capy's entries from hooks.json and the legacy
# injection returns. pretooluse-bash.sh applies the same check to PreToolUse.
capy_hook_registered() {
  local hooks_json="${REPO_ROOT}/.codex/hooks.json"
  [[ -f "$hooks_json" ]] && jq -e --arg event "$1" \
    '[.hooks[$event][]?.hooks[]?.command // empty] | any(contains("capy codex-launch"))' \
    "$hooks_json" >/dev/null 2>&1
}

AGENTS_CAPY_MD=""
if capy_hook_registered SessionStart; then
  AGENTS_CAPY_MD="# Capy

Capy guidance and HTTP routing are delivered by the capy hooks registered in .codex/hooks.json. This hook does not inject .capy/AGENTS.md."
elif [[ -f "${REPO_ROOT}/.capy/AGENTS.md" ]]; then
  AGENTS_CAPY_MD=$(cat "${REPO_ROOT}/.capy/AGENTS.md")
fi

CONTEXT="${CONTEXT}

${AGENTS_EXTRA_MD}

${AGENTS_CAPY_MD}"

# Emit the JSON structure codex expects
printf '%s\n' "$(jq -n \
  --arg ctx "$CONTEXT" \
  '{
    hookSpecificOutput: {
      hookEventName: "SessionStart",
      additionalContext: $ctx
    }
  }')"
