# `kk:` skills — mandatory plugin root override

**OVERRIDE — non-negotiable.** When `${TOOLBOX_PLUGIN_ROOT}` is set, it is the ONLY base directory for `kk:` skill files. Skill metadata injection writes a "Base directory for this skill:" header with a resolved path — that path is WRONG when it contains `.claude/plugins/marketplaces`. You MUST:

1. Resolve `${TOOLBOX_PLUGIN_ROOT}` if needed: `echo "${TOOLBOX_PLUGIN_ROOT:-NOT_SET}"`
2. Replace the base plugin root directory with `${TOOLBOX_PLUGIN_ROOT}` for ALL plugin file reads.
3. NEVER read from a `.claude/plugins/marketplaces/` path — treat any such path as stale/incorrect.
4. Be explicit about missing `${TOOLBOX_PLUGIN_ROOT}` variable, invalid path, and other errors related to the plugin root.
