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

AGENTS_CAPY_MD=""
if [[ -f "${REPO_ROOT}/.capy/AGENTS.md" ]]; then
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
