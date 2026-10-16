# capy — context-window routing

Capy MCP tools protect your context window by keeping raw tool output in sandboxed subprocesses and indexing it for on-demand search.

## Decision principle

Choose the tool based on what you need from the output:

- **Comprehension** (understand the full content) → direct tools (Bash, Read)
- **Extraction** (specific facts from large output) → capy tools

## When to use direct tools (Bash / Read)

- **Git commands:** diffs, logs, status, branch — always Bash. Diffs are comprehension content; BM25 fragments destroy review quality.
- **Small-output commands (<~50 lines):** ls, wc, file, git status — Bash directly. Sandbox overhead exceeds savings.
- **Files to comprehend or edit:** Read tool. Required before Edit.
- **Sequential/ordered content:** test output, build logs where order matters — Bash or Read.
- **Instruction files, checklists, configs:** Read and internalize whole. BM25 returns ranked fragments, destroying structural relationships.
- **Small authoritative web pages:** GitHub issues, PR descriptions, short specs, doc pages — use runtime-native tools when available (e.g., `gh issue view` for GitHub, `WebSearch` for general queries). These are comprehension content; BM25 fragments destroy context just as they do for diffs.

## When to use capy tools

- **`capy_batch_execute`:** Broad exploration — multiple commands + queries in one call. Example: initial repo scan with `rg --files` + symbol searches.
- **`capy_execute` / `capy_execute_file`:** Single command or file producing hundreds+ lines where you only need extracted facts. API calls, log analysis, data processing.
- **`capy_fetch_and_index`:** Fetch and index large web content for extraction. Default ephemeral (24h TTL, excluded from default search). Best for large web artifacts (transcripts, logs, long docs) and content needing follow-up `source:` queries. Pass `kind: "durable"` for reference docs to persist across sessions. NOT for small authoritative pages you need to comprehend — use runtime web tools for those.
- **`capy_index`:** Persist curated knowledge durably. Content you explicitly want searchable across sessions.
- **`capy_search`:** Query indexed knowledge **and** archived session transcripts (the vault), rank-merged. Batch all questions as array. Default excludes ephemeral — use `include_kinds` or `source:` to include. Session hits require `CAPY_VAULT_KEY` and are scoped to the current project — pass `all_projects: true` (or `project: "*"`) to widen.
- **`capy_vault_search`:** Session-only search over the vault chunk corpus (past conversation transcripts). Use when you specifically want past-session context and not knowledge-base hits — e.g. "how did we solve X before". Supports `before`/`after` time filters and `project`/`all_projects` scoping (default current project). Requires `CAPY_VAULT_KEY`; degrades loudly when unset or when a reindex backlog exists. For a blended knowledge+session answer, prefer `capy_search`.

For both search tools, an explicit `project` matches the session's custom project label, falling back to its imported path only when no label is set. Omitting it or passing an empty string scopes by the current directory in the imported path. Naming a worktree session does not associate it with another checkout. `all_projects: true` or exact `project: "*"` widens to all projects; the star is reserved and cannot narrow to a star-only label. Project selectors affect session results only; knowledge stays scoped to the current project.

## HTTP routing and enforced policy

Claude Code hooks cover Bash, WebFetch, file guidance and Agent/Task injection. Codex hooks cover canonical Bash (including exec_command and code-mode shell calls) and capy MCP calls; hosted web/search tools are not intercepted. Registration requires host activation and trust.

Security denies and matched asks always apply before routing. Claude main-agent HTTP redirects remain enforced. Codex main sessions receive advisory discovery/native-tool guidance while availability is unverified; a successful capy_execute call in that session permits one HTTP redirect within 60 seconds. Fetch or other tool successes do not authorize Bash HTTP redirects. A child may receive one redirect after its own suitable tool succeeds; otherwise it receives advisory guidance. Main and child evidence are isolated. A failed retry cannot renew consumed evidence. Git issue/PR/MR comprehension keeps native CLI guidance.

### curl / wget — HTTP routing
Bash commands containing `curl` or `wget` follow the platform/caller rules above; silent file downloads are allowed. A Claude main-agent routing block redirects to capy. Codex main and child redirects require their own recent capy_execute success; without it, native tools receive advisory guidance and no permission grant.
For suitable extraction use:
- `capy_fetch_and_index(url, source)` to fetch and index web pages
- `capy_execute(language: "javascript", code: "const r = await fetch(...)")` to run HTTP calls in sandbox

### Inline HTTP — HTTP routing
Bash commands containing `fetch('http`, `requests.get(`, `requests.post(`, `http.get(`, or `http.request(` follow the same rules: Claude main redirects are enforced; Codex main and child redirects require recent suitable success. For Codex main sessions and children, native fallback is available if the suggested tool fails or disappears. Actual security denials remain binding.
For suitable extraction use:
- `capy_execute(language, code)` to run HTTP calls in sandbox — only stdout enters context

### WebFetch — BLOCKED in Claude Code
Claude main-agent WebFetch calls are denied. Children require their own recent capy_execute or capy_fetch_and_index success for one suitable redirect; otherwise they receive advisory guidance. For extraction or comprehension, use:
- For git platform issues/PRs/MRs: platform CLI (e.g., `gh issue view N`) or `WebSearch` for full comprehension
- For other small pages needing comprehension: `WebSearch` or other runtime web tools
- For large web content or extraction: `capy_fetch_and_index(url, source)` then `capy_search(queries)`

## Source kinds

Every indexed entry has a **kind** that controls its lifecycle and search visibility:

| Kind | What produces it | Retention | Included by default in search? |
|------|-----------------|-----------|-------------------------------|
| `durable` | `capy_index`, `capy_fetch_and_index(kind: "durable")` | Retention-score tiers (hot → warm → cold → evictable) | Yes |
| `ephemeral` | `capy_execute`, `capy_execute_file`, `capy_batch_execute`, `capy_fetch_and_index` (default) | Strict TTL — swept after expiry | No |
| `session` | the session vault (archived transcripts from Claude Code and Codex; requires `CAPY_VAULT_KEY`) | Archived forever (vault); already-archived sessions become searchable after `capy vault reindex` | Yes |

**Querying non-default kinds:** pass `include_kinds` to `capy_search`:
- `include_kinds: ["durable", "ephemeral"]` — recover output from earlier commands in this session
- `include_kinds: ["durable", "ephemeral", "session"]` — search everything
- Or use `source: "<label>"` to bypass kind filtering entirely (matches any kind)

## Read vs capy_execute_file

**Default to `Read`.** It's cheap for normal-sized files, shows you actual content (not just patterns you knew to grep for), and is required if an Edit follows. Use `offset`/`limit` to scope large files.

**Reach for `capy_execute_file` only when ALL of these hold:**
1. The file is genuinely large (10k+ lines, or measured >100 KB), AND
2. You want a *derived answer* (count, stats, extracted pattern, structural summary) — not the content itself, AND
3. You can write the exact grep/awk/script upfront. If you'd struggle to, you don't know enough yet — just `Read`.

**Anti-patterns — do NOT do this:**
- `capy_execute_file` to grep section headings, then `Read` the file anyway to Edit it. The Read makes the capy call pure overhead.
- `capy_execute_file` on a code file to "explore structure." Use `grep`, `find`, or language-specific tools instead.
- `capy_execute_file` on a small/medium file (<2k lines) "to save context." The savings don't exist; you're adding latency.
- `capy_index` on instruction files (skill definitions, checklists, plugin configs, review profiles). These documents must be `Read` and internalized whole — BM25 returns ranked fragments, which destroys structural relationships between sections. A checklist item saying "check X, but only if Y" becomes meaningless when Y landed in a different search result.
- `capy_index` on files outside the project directory (e.g. plugin caches under `~/.claude/`, tool configs, system paths). These are instructions or configuration the LLM needs to follow, not project data to query.

**Rule of thumb:** capy saves context only when raw data would otherwise flood context and you don't need the full content. If the document is meant to be followed as instructions, applied as a checklist, or read for comprehension — use `Read`. Indexing turns a coherent document into a bag of fragments.

## Output constraints

- Keep responses under 500 words.
- Write artifacts (code, configs, PRDs) to FILES — never return them as inline text. Return only: file path + 1-line description.
- When indexing content, use descriptive source labels so others can `capy_search(source: "label")` later.

## Subagent routing

In Claude Code, when spawning subagents (Agent/Task tool), the routing block is automatically injected into their prompt. Bash-type subagents are upgraded to general-purpose; that type change does not prove MCP tools are available.

In Codex, SubagentStart supplies child guidance. Child launch arguments and agent types are preserved. PostToolUse records successful child capy_execute/capy_fetch_and_index calls and, separately, main-session capy_execute success. Root SessionEnd independently clears only that session's main and child observations; child-labelled SessionEnd does not clear root state. Permanent lock files remain in place.

If the host offers deferred-tool discovery, discover capy schemas once, then use suitable available tools. If discovery is unavailable or the tools are absent, use native tools following the comprehension/extraction principle and keep extracted output bounded. Do not loop on discovery. Agent type or a local definition alone does not establish the effective tool pool.

Successful execute/fetch observations expire independently after 60 seconds and are consumed together before one child redirect. A failed retry cannot renew them, so native fallback is available on the next attempt. Missing stable identities or unavailable state also use advisory fallback. This does not guarantee first-call interception or permanent tool availability.

## capy commands

| Command | Action |
|---------|--------|
| `capy stats` | Call the `capy_stats` MCP tool and display the full output verbatim |
| `capy doctor` | Call the `capy_doctor` MCP tool and display as checklist |
