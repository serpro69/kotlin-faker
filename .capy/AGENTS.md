# capy — context-window routing

Capy captures execution output in a separate encrypted run store and returns bounded results. Knowledge and session search remain separate.

## Decision principle

Use capture for routine tests, builds and dependency installs. Codex enforces recognized commands for root and child agents; Claude Code receives guidance only. For other work, choose the tool based on what you need from the output:

- **Comprehension** (understand the full content) → direct tools (Bash, Read)
- **Extraction** (specific facts from large output) → capy tools

## When to use direct tools (Bash / Read)

- **Git commands:** diffs, logs, status, branch — always Bash. Diffs are comprehension content; BM25 fragments destroy review quality.
- **Small-output non-routine commands (<~50 lines):** ls, wc, file, git status — Bash directly. Sandbox overhead exceeds savings.
- **Files to comprehend or edit:** Read tool. Required before Edit.
- **Existing sequential/ordered content:** read existing test and build logs directly. New routine runs use capture; recover ordered output with capy_runs read or capy runs read. Live native execution in Codex requires an explicit operator exception.
- **Governing instructions, checklists, agent configs:** Read and internalize whole. BM25 returns ranked fragments, destroying structural relationships.
- **Small authoritative web pages:** GitHub issues, PR descriptions, short specs, doc pages — use runtime-native tools when available (e.g., `gh issue view` for GitHub, `WebSearch` for general queries). These are comprehension content; BM25 fragments destroy context just as they do for diffs.

## When to use capy tools

- **`capy_batch_execute`:** Multiple commands with encrypted capture, bounded status and optional run-log queries in one call. Example: initial repo scan with `rg --files` + symbol searches.
- **`capy_execute` / `capy_execute_file`:** Execute once with encrypted capture and bounded output for routine commands or extraction, with or without intent. Retain the run ID; inspect status and recover logs without repeating execution.
- **`capy_runs`:** Status, list, ordered read, search and owner-local cancel for retained runs/batches. CLI alternatives are `capy execute --code '<program>'` and `capy runs`. A lost response or post-spawn capture failure requires recovery before retrying. Admission failures require repair, not native execution.
- **`capy_fetch_and_index`:** Fetch and index large web content for extraction. Default ephemeral (24h TTL, excluded from default search). Best for large web artifacts (transcripts, logs, long docs) and content needing follow-up `source:` queries. Pass `kind: "durable"` for reference docs to persist across sessions. NOT for small authoritative pages you need to comprehend — use runtime web tools for those.
- **`capy_index`:** Persist curated knowledge durably. Content you explicitly want searchable across sessions.
- **`capy_search`:** Query indexed knowledge **and** archived session transcripts (the vault), rank-merged. Batch all questions as array. Default excludes ephemeral — use `include_kinds` or `source:` to include. Session hits require `CAPY_VAULT_KEY` and are scoped to the current project — pass `all_projects: true` (or `project: "*"`) to widen.
- **`capy_vault_search`:** Session-only search over the vault chunk corpus (past conversation transcripts). Use when you specifically want past-session context and not knowledge-base hits — e.g. "how did we solve X before". Supports `before`/`after` time filters and `project`/`all_projects` scoping (default current project). Requires `CAPY_VAULT_KEY`; degrades loudly when unset or when a reindex backlog exists. For a blended knowledge+session answer, prefer `capy_search`.

For both search tools, an explicit `project` matches the session's custom project label, falling back to its imported path only when no label is set. Omitting it or passing an empty string scopes by the current directory in the imported path. Naming a worktree session does not associate it with another checkout. `all_projects: true` or exact `project: "*"` widens to all projects; the star is reserved and cannot narrow to a star-only label. Project selectors affect session results only; knowledge stays scoped to the current project.

## HTTP routing and enforced policy

Claude Code hooks cover Bash, WebFetch, file guidance and Agent/Task injection. Codex hooks cover canonical Bash (including exec_command and code-mode shell calls) and capy MCP calls; hosted web/search tools are not intercepted. Registration requires host activation and trust.

Security denies and matched asks always apply before routing. Claude main-agent HTTP redirects remain enforced. For HTTP-only requests, Codex main sessions receive advisory discovery/native-tool guidance while availability is unverified; a successful capy_execute call in that session permits one HTTP redirect within 60 seconds. Fetch or other tool successes do not authorize Bash HTTP redirects. A child may receive one redirect after its own suitable tool succeeds; otherwise it receives advisory guidance. Main and child evidence are isolated. For execute, a healthy running run ID with recording capture or exited-zero run with complete capture counts. Unstarted/queued, failed/nonzero/incomplete runs and status/read/search/cancel polling never create or refresh evidence. Fetch and legacy response handling remain separate. A failed retry cannot renew consumed evidence. Git issue/PR/MR comprehension keeps native CLI guidance.

### Routine capture — enforced in Codex

Codex evaluates whole-request security, including captured CLI programs, before routine capture policy and HTTP-only routing. A mixed HTTP/routine request is denied before any side effect, evidence consumption or native-fallback advice. Each uncaptured routine element must be excepted independently; a captured sibling cannot exempt it. Claude has no routine enforcement gate.

The literal catalogue covers go test/build; cargo test/build/check; pytest and python -m pytest; pip install and python -m pip install; npm/pnpm/yarn install/ci/add/test/build/run; make/gmake targets; gradle/gradlew, mvn/mvnw, sbt and mise run. Assignment, env, command, literal shell -c and mise exec wrappers are inspected. Help/version probes are excluded; dynamic executables, aliases and script bodies are outside this targeted recognizer and receive guidance.

Use `capy_execute` or `capy_batch_execute`; `capy execute` is a CLI capture alternative, not an availability guarantee. Do not add tail/grep filters to avoid capture. If capture cannot start, stop and repair it. Unknown outcomes require `capy_runs` / `capy runs` recovery before retrying. Missing tools or exhausted capacity do not authorize native routine execution.

Operator configuration `[routing.routine] enabled = false` disables this Codex output gate; `native_allow = ["Bash(make debug:*)"]` excepts matching routine elements using existing Bash-pattern semantics. Neither bypasses security. Reload MCP servers and verify execute/runs support before enabling new hooks. Disable the routine gate before downgrading. Setup does not change trust or prove activation.

Direct code-file capture uses checked owner-relative Read admission, including matched asks. Ambiguous launch contexts (compound requests, assignment/env/shell/mise wrappers or nested captures) require a direct standalone code-file invocation or literal inline code.

### curl / wget — HTTP routing
Bash commands containing `curl` or `wget` follow the platform/caller rules above; silent file downloads are allowed. A Claude main-agent routing block redirects to capy. Codex main and child redirects require their own recent capy_execute success; without it, native tools receive advisory guidance and no permission grant.
For suitable extraction use:
- `capy_fetch_and_index(url, source)` to fetch and index web pages
- `capy_execute(language: "javascript", code: "const r = await fetch(...)")` to run HTTP calls in sandbox

### Inline HTTP — HTTP routing
Bash commands containing `fetch('http`, `requests.get(`, `requests.post(`, `http.get(`, or `http.request(` follow the same rules: Claude main redirects are enforced; Codex main and child redirects require recent suitable success. For HTTP-only requests in Codex main sessions and children, native fallback is available if the suggested tool fails or disappears. Actual security denials remain binding.
For suitable extraction use:
- `capy_execute(language, code)` to run HTTP calls in sandbox — only stdout enters context

### WebFetch — BLOCKED in Claude Code
Claude main-agent WebFetch calls are denied. Children require their own recent capy_execute or capy_fetch_and_index success for one suitable redirect; otherwise they receive advisory guidance. For extraction or comprehension, use:
- For git platform issues/PRs/MRs: platform CLI (e.g., `gh issue view N`) or `WebSearch` for full comprehension. For large-content extraction, run the CLI through `capy_execute`, then read or search its captured output with `capy_runs`.
- For other small pages needing comprehension: `WebSearch` or other runtime web tools
- For large content from other URLs: `capy_fetch_and_index(url, source)` then `capy_search(queries)`

## Source kinds

Every indexed entry has a **kind** that controls its lifecycle and search visibility:

| Kind | What produces it | Retention | Included by default in search? |
|------|-----------------|-----------|-------------------------------|
| `durable` | `capy_index`, `capy_fetch_and_index(kind: "durable")` | Retention-score tiers (hot → warm → cold → evictable) | Yes |
| `ephemeral` | `capy_fetch_and_index` (default), plus retained legacy execute/file/batch sources | Strict TTL — swept after expiry | No |
| `session` | the session vault (archived transcripts from Claude Code and Codex; requires `CAPY_VAULT_KEY`) | Archived forever (vault); already-archived sessions become searchable after `capy vault reindex` | Yes |

New execution logs live under the selected owner's .capy/runs/runs.db, not in knowledge/vault sources. Use capy_runs or capy runs for recovery. Retention defaults to 24 hours with quota-based eviction; complete recovery requires retained output and complete capture.

**Querying non-default kinds:** pass `include_kinds` to `capy_search`:
- `include_kinds: ["durable", "ephemeral"]` — recover legacy ephemeral sources and fetched content
- `include_kinds: ["durable", "ephemeral", "session"]` — search everything
- Or use `source: "<label>"` to bypass kind filtering entirely (matches any kind)

## Read vs capy_execute_file

Default to reading the relevant content with `Read`. Use `offset`/`limit` to scope large files.

Use `capy_execute_file` for a specific question when it avoids loading substantial irrelevant content. If you cannot specify what to extract, read the relevant content first. **Do not perform extraction that merely duplicates a read you still need.**

- The document is meant to be followed as instructions, applied as a checklist, or read for comprehension — use `Read`. Indexing turns a coherent document into a bag of fragments.
- Finding tests related to X → search for candidates, then use `Read` on the relevant test blocks.
- Extracting *derived answers* (count, stats, extracted pattern, structural summary), not the content itself; for example, counting services with a flag enabled → use `capy_execute_file` to extract the count.

**Anti-patterns — do NOT do this:**
- `capy_index` on instruction files (skill definitions, checklists, plugin configs, review profiles). These documents must be `Read` and internalized whole — BM25 returns ranked fragments, which destroys structural relationships between sections. A checklist item saying "check X, but only if Y" becomes meaningless when Y landed in a different search result.
- `capy_index` on files outside the project directory (e.g. plugin caches under `~/.claude/`, tool configs, system paths). These are instructions or configuration the LLM needs to follow, not project data to query.

## Source labels

- When indexing content, use descriptive source labels so others can `capy_search(source: "label")` later.

## Subagent routing

In Claude Code, when spawning subagents (Agent/Task tool), the routing block is automatically injected into their prompt. Bash-type subagents are upgraded to general-purpose; that type change does not prove MCP tools are available.

In Codex, SubagentStart supplies child guidance. Child launch arguments and agent types are preserved. PostToolUse records successful child capy_execute/capy_fetch_and_index calls and, separately, main-session capy_execute success. Root SessionEnd independently clears only that session's main and child observations; child-labelled SessionEnd does not clear root state. Permanent lock files remain in place.

If the host offers deferred-tool discovery, discover capy schemas once, then use suitable available tools. If discovery is unavailable or the tools are absent, use native tools only where policy permits and keep output bounded. Codex routine capture requirements still apply. Do not loop on discovery. Agent type or a local definition alone does not establish the effective tool pool.

Successful execute/fetch observations expire independently after 60 seconds and are consumed together before one child redirect. A failed retry cannot renew them, so native fallback is available for HTTP-only requests on the next attempt. Missing stable identities or unavailable state also use advisory fallback for HTTP-only routing; mandatory routine capture remains independent of observations.

## capy commands

| Command | Action |
|---------|--------|
| `capy stats` | Call the `capy_stats` MCP tool |
| `capy doctor` | Call the `capy_doctor` MCP tool |
