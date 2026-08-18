#!/usr/bin/env bash
#
# template-sync.sh - Synchronize configuration from upstream claude-toolbox template
#
# This script fetches template updates from the upstream repository and applies
# project-specific substitutions using values stored in the state manifest.
#
# USAGE:
#   ./template-sync.sh                      # Sync to latest release
#   ./template-sync.sh --version v1.2.0     # Sync to specific version
#   ./template-sync.sh --dry-run            # Preview what would change
#   ./template-sync.sh --ci                 # CI mode for GitHub Actions
#   ./template-sync.sh --local                         # Fetch, compare, and apply locally
#   ./template-sync.sh --apply --output-dir ./staging  # Apply staged changes (CI)
#
# OPTIONS:
#   --version VERSION     Target version to sync (default: latest)
#                         - "latest": Most recent tagged release
#                         - "main": Latest from main branch
#                         - "v1.2.3": Specific tag
#   --dry-run             Preview changes without applying them
#   --local               Fetch, compare, and apply in a single invocation
#   --apply               Apply pre-staged changes to the working tree (used by CI)
#   --ci                  CI mode for GitHub Actions (structured output)
#   --output-dir DIR      Directory for staged changes (default: temp)
#   -h, --help            Show this help message
#
# REQUIRES:
#   - jq (for JSON parsing)
#   - git
#   - curl
#
# EXIT CODES:
#   0 - Success (with or without changes)
#   1 - Operational error (missing manifest, network failure, invalid JSON)
#   2 - Invalid CLI arguments
#
# TROUBLESHOOTING:
#   "Manifest not found":
#     - Ensure .github/template-state.json exists
#     - For repos created before sync feature, create manifest manually (see README)
#
#   "Version not found":
#     - Check available tags: git ls-remote --tags https://github.com/serpro69/claude-toolbox
#     - Use 'latest' for most recent release or 'main' for bleeding edge
#
#   "Network error":
#     - Verify internet connectivity
#     - Check if upstream repo is accessible
#     - Script will retry 3 times with 5s delay
#
#   "Invalid JSON in manifest":
#     - Check manifest file for syntax errors
#     - Restore from version control if corrupted

set -euo pipefail

# Source semver comparison (provides compare() function).
# Bootstrap: downstream repos may not have this file yet if they haven't synced
# since it was introduced — fetch from upstream so the script stays self-contained.
# shellcheck source=semver-compare.sh
_semver_path="$(dirname "${BASH_SOURCE[0]}")/semver-compare.sh"
if [[ ! -f "$_semver_path" ]]; then
  curl -fsSL "https://raw.githubusercontent.com/serpro69/claude-toolbox/master/.claude/toolbox/scripts/semver-compare.sh" \
    -o "$_semver_path" 2>/dev/null || {
    echo "Failed to fetch semver-compare.sh" >&2
    exit 1
  }
fi
source "$_semver_path"
unset _semver_path

# =============================================================================
# Global Configuration
# =============================================================================

MANIFEST_PATH=".github/template-state.json"
STAGING_DIR=""
DRY_RUN=false
CI_MODE=false
TARGET_VERSION="latest"
FETCHED_TEMPLATES_PATH=""
SUBSTITUTED_TEMPLATES_PATH=""

# Temp directory for cleanup tracking (set when using auto-generated staging dir)
TEMP_DIR=""

# File change tracking arrays
ADDED_FILES=()
MODIFIED_FILES=()
DELETED_FILES=()
UNCHANGED_FILES=()

# Exclusion tracking arrays
EXCLUDED_FILES=()
SYNC_EXCLUSIONS=()

# Built-in exclusions: files that fetch_upstream_templates strips from staging
# because they're per-repo and must never be synced downstream. These patterns
# must also be honored during deletion detection — otherwise compare_files sees
# the file in the project but not in staging and incorrectly flags it as deleted.
# Keep this list in sync with the strip logic in fetch_upstream_templates().
BUILTIN_EXCLUSIONS=(
  ".claude/settings.local.json"
  ".claude/capy/*"
  ".claude/scripts/capy.sh"
  ".codex/scripts/capy.sh"
)

# Resolved version (for reporting)
RESOLVED_VERSION=""

# Plugin migration tracking
PLUGIN_MIGRATED=false

# Apply mode: when true, migration functions perform filesystem mutations.
# When false (default), they only populate tracking arrays for reporting.
APPLY_MODE=false

# Local mode: fetch + compare + apply in a single invocation
LOCAL_MODE=false

# =============================================================================
# Color Output
# =============================================================================

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
CYAN='\033[0;36m'
BOLD='\033[1m'
NC='\033[0m' # No Color

# =============================================================================
# Logging Functions
# =============================================================================

log_info() {
  echo -e "${GREEN}[INFO]${NC} $1"
}

log_warn() {
  echo -e "${YELLOW}[WARN]${NC} $1"
}

log_error() {
  echo -e "${RED}[ERROR]${NC} $1" >&2
}

log_success() {
  echo -e "${GREEN}✓${NC} $1"
}

log_step() {
  echo -e "${CYAN}>>>${NC} $1"
}

# =============================================================================
# Cleanup Functions
# =============================================================================

# Cleanup handler that preserves exit code
# Called on EXIT, INT, TERM signals
cleanup_on_exit() {
  local exit_code=$?

  # Only clean up if TEMP_DIR is set and exists
  if [[ -n "${TEMP_DIR:-}" && -d "$TEMP_DIR" ]]; then
    rm -rf "$TEMP_DIR"
    if ! $CI_MODE; then
      log_info "Cleaned up temporary directory"
    fi
  fi

  exit $exit_code
}

# =============================================================================
# Helper Functions
# =============================================================================

# is_excluded()
# Checks if a file path matches any exclusion pattern.
#
# Args:
#   $1 - Project-relative file path (e.g., ".claude/commands/chain-of-verification/default.md")
#
# Returns:
#   0 if path matches an exclusion pattern (excluded)
#   1 if path does not match any pattern (not excluded)
#
# Note: Uses bash case statement glob matching where * matches any characters including /
is_excluded() {
  local path="$1"
  local pattern

  # Check built-in exclusions first (per-repo files stripped from staging)
  for pattern in "${BUILTIN_EXCLUSIONS[@]}"; do
    # IMPORTANT: pattern must be unquoted for glob expansion in case
    case "$path" in
    $pattern)
      return 0 # Excluded
      ;;
    esac
  done

  # Check user-configured exclusions from manifest
  for pattern in "${SYNC_EXCLUSIONS[@]}"; do
    case "$path" in
    $pattern)
      return 0 # Excluded
      ;;
    esac
  done

  return 1 # Not excluded
}

# =============================================================================
# Dependency Check
# =============================================================================

check_dependencies() {
  local missing=()

  if ! command -v jq &>/dev/null; then
    missing+=("jq")
  fi

  if ! command -v git &>/dev/null; then
    missing+=("git")
  fi

  # curl and yq are only needed for fetch/substitute, not apply mode
  if ! $APPLY_MODE; then
    if ! command -v curl &>/dev/null; then
      missing+=("curl")
    fi

    if ! command -v yq &>/dev/null; then
      missing+=("yq")
    fi
  fi

  if [[ ${#missing[@]} -gt 0 ]]; then
    log_error "Missing required dependencies: ${missing[*]}"
    echo "Please install the missing dependencies:"
    echo "  macOS:  brew install ${missing[*]}"
    echo "  Linux:  apt-get install ${missing[*]}"
    exit 1
  fi
}

# json_update()
# Atomically updates a JSON file using a jq expression.
# Writes to a temp file first, then moves into place on success.
#
# Args:
#   $1 - Target file path
#   $2 - jq filter expression
#   $@ - Additional arguments forwarded to jq (e.g., --arg key val)
#
# Returns:
#   0 on success, 1 on jq failure (target file unchanged)
json_update() {
  local file="$1" expr="$2"
  shift 2
  local tmp
  tmp=$(mktemp "/tmp/json-update.XXXXXX")
  if jq "$@" "$expr" "$file" >"$tmp"; then
    mv "$tmp" "$file"
    return 0
  else
    rm -f "$tmp"
    return 1
  fi
}

# =============================================================================
# Manifest Functions
# =============================================================================

# get_manifest_value()
# Extracts a value from the manifest file using a jq expression.
#
# Args:
#   $1 - jq expression to evaluate (e.g., '.variables.PROJECT_NAME')
#
# Returns:
#   Extracted value via stdout, or empty string if not found
#
# Example:
#   project_name=$(get_manifest_value '.variables.PROJECT_NAME')
get_manifest_value() {
  local jq_expr="$1"
  jq -r "$jq_expr" "$MANIFEST_PATH"
}

# read_manifest()
# Reads and validates the manifest file exists and contains valid JSON.
# Verifies required top-level fields (schema_version, upstream_repo, template_version, variables).
#
# Returns:
#   0 on success (manifest loaded)
#   Exits with 1 if manifest missing, invalid JSON, or missing required fields
#
# Side effects:
#   Logs info/error messages
read_manifest() {
  # Check if manifest file exists
  if [[ ! -f "$MANIFEST_PATH" ]]; then
    log_error "Manifest file not found: $MANIFEST_PATH"
    log_error ""
    log_error "This repository doesn't have a template state manifest."
    log_error "Possible reasons:"
    log_error "  - The repository was created before the sync feature was available"
    log_error "  - The cleanup script was run with an older version"
    log_error "  - The manifest file was accidentally deleted"
    log_error ""
    log_error "To create a manifest manually, see the template documentation."
    exit 1
  fi

  # Validate JSON syntax
  if ! jq -e '.' "$MANIFEST_PATH" &>/dev/null; then
    log_error "Invalid JSON in manifest file: $MANIFEST_PATH"
    log_error "The manifest file is not valid JSON. It may be corrupted."
    log_error "Please check the file for syntax errors or restore it from version control."
    exit 1
  fi

  # Verify required top-level fields exist
  local required_fields=("schema_version" "upstream_repo" "template_version" "variables")
  for field in "${required_fields[@]}"; do
    if [[ "$(get_manifest_value ".$field // empty")" == "" ]]; then
      log_error "Missing required field in manifest: $field"
      log_error "The manifest file may be incomplete or corrupted."
      exit 1
    fi
  done

  log_info "Manifest loaded: $MANIFEST_PATH"

  # Load sync exclusions if present (optional field)
  if jq -e '.sync_exclusions' "$MANIFEST_PATH" &>/dev/null; then
    mapfile -t SYNC_EXCLUSIONS < <(jq -r '.sync_exclusions[]' "$MANIFEST_PATH")
    if [[ ${#SYNC_EXCLUSIONS[@]} -gt 0 ]]; then
      log_info "Loaded ${#SYNC_EXCLUSIONS[@]} sync exclusion pattern(s)"
    fi
  fi
}

# validate_manifest()
# Validates manifest schema version and all required variables.
# Checks schema_version is supported (currently: "1") and validates
# upstream_repo format and required variable presence.
#
# Returns:
#   0 on success (manifest valid)
#   Exits with 1 if validation fails
#
# Side effects:
#   Logs success/error messages
validate_manifest() {
  # Check schema version
  local schema_version
  schema_version=$(get_manifest_value '.schema_version // empty')

  if [[ -z "$schema_version" ]]; then
    log_error "Invalid manifest: missing schema_version"
    log_error "The manifest file may be corrupted or from an incompatible version."
    exit 1
  fi

  if [[ "$schema_version" != "1" ]]; then
    log_error "Manifest schema version $schema_version is not supported"
    log_error "This sync script supports schema version 1."
    log_error "Please update the template-sync script or migrate your manifest."
    exit 1
  fi

  # Validate upstream_repo format (owner/repo)
  local upstream_repo
  upstream_repo=$(get_manifest_value '.upstream_repo')
  if [[ ! "$upstream_repo" =~ ^[^/]+/[^/]+$ ]]; then
    log_error "Invalid upstream_repo format: $upstream_repo (expected: owner/repo)"
    exit 1
  fi

  # Verify all required variables exist (can be empty but must be present)
  local required_vars=("PROJECT_NAME" "LANGUAGES" "CC_MODEL")
  for var in "${required_vars[@]}"; do
    if [[ "$(get_manifest_value ".variables.$var // \"__MISSING__\"")" == "__MISSING__" ]]; then
      log_error "Missing required variable in manifest: $var"
      exit 1
    fi
  done

  # Validate LANGUAGES is not empty
  local languages
  languages=$(get_manifest_value '.variables.LANGUAGES')
  if [[ -z "$languages" ]]; then
    log_error "LANGUAGES variable cannot be empty in manifest"
    exit 1
  fi

  # Validate sync_exclusions if present (optional field)
  if jq -e '.sync_exclusions' "$MANIFEST_PATH" &>/dev/null; then
    # Must be an array
    if ! jq -e '.sync_exclusions | type == "array"' "$MANIFEST_PATH" &>/dev/null; then
      log_error "sync_exclusions must be an array"
      exit 1
    fi
    # All elements must be strings
    if ! jq -e '.sync_exclusions | all(type == "string")' "$MANIFEST_PATH" &>/dev/null; then
      log_error "All sync_exclusions elements must be strings"
      exit 1
    fi
  fi

  log_success "Manifest validation passed"
}

# migrate_manifest()
# Migrates the manifest's upstream_repo from the old repository name
# (serpro69/claude-starter-kit) to the new name (serpro69/claude-toolbox).
# This ensures existing users' manifests are updated on their next sync.
#
# Returns:
#   0 on success (migration applied or not needed)
#   Exits with 1 if manifest rewrite fails
#
# Side effects:
#   Rewrites MANIFEST_PATH in-place if migration needed
#   Reloads manifest via read_manifest() after rewrite
#   Logs info message when migration is triggered
migrate_manifest() {
  local upstream_repo
  upstream_repo=$(get_manifest_value '.upstream_repo')

  if [[ "$upstream_repo" == "serpro69/claude-starter-kit" ]]; then
    log_info "Migrating upstream_repo from serpro69/claude-starter-kit to serpro69/claude-toolbox"
    if ! json_update "$MANIFEST_PATH" '.upstream_repo = "serpro69/claude-toolbox"'; then
      log_error "Failed to migrate manifest"
      exit 1
    fi
    read_manifest
  fi
}

# backfill_manifest_variables()
# Adds missing optional variables to the manifest with their default values.
# This ensures the manifest is always explicit about what values are being used,
# even for variables that were introduced after the downstream repo was created.
#
# Returns:
#   0 on success
#
# Side effects:
#   Rewrites MANIFEST_PATH in-place if any variables are added
#   Reloads manifest via read_manifest() after rewrite
#   Logs info message listing backfilled variables
backfill_manifest_variables() {
  local defaults=(
    "CC_STATUSLINE:enhanced"
    "CC_EFFORT_LEVEL:high"
    "CC_PERMISSION_MODE:default"
    "CODEX_MODEL:gpt-5.6-sol"
    "CODEX_APPROVAL_POLICY:on-request"
    "SKIP_CAPY:false"
  )

  local needs_update=false
  local backfilled=()

  for entry in "${defaults[@]}"; do
    local var="${entry%%:*}"
    local default_val="${entry#*:}"
    if [[ "$(get_manifest_value ".variables.$var // \"__MISSING__\"")" == "__MISSING__" ]]; then
      needs_update=true
      backfilled+=("$var=$default_val")
    fi
  done

  if $needs_update; then
    local jq_expr='.'
    for entry in "${defaults[@]}"; do
      local var="${entry%%:*}"
      local default_val="${entry#*:}"
      if [[ "$(get_manifest_value ".variables.$var // \"__MISSING__\"")" == "__MISSING__" ]]; then
        jq_expr="$jq_expr | .variables.$var = \"$default_val\""
      fi
    done
    if ! json_update "$MANIFEST_PATH" "$jq_expr"; then
      log_warn "Failed to backfill manifest variables"
      return 0
    fi
    log_info "Backfilled missing manifest variables: ${backfilled[*]}"
    read_manifest
  fi
}

# =============================================================================
# Plugin Migration Functions
# =============================================================================

# needs_plugin_migration()
# Checks whether the downstream repo needs to migrate from template-managed
# skills/commands/hooks to the kk plugin system.
#
# Args:
#   $1 - Path to fetched upstream directory (parent of klaude-plugin/)
#
# Returns:
#   0 if migration is needed
#   1 if migration is not needed (already migrated or upstream has no plugin)
needs_plugin_migration() {
  local upstream_dir="$1"

  # Check if upstream has the plugin
  if [[ ! -f "$upstream_dir/klaude-plugin/.claude-plugin/plugin.json" ]]; then
    return 1
  fi

  # Check if already migrated
  if [[ -f "$MANIFEST_PATH" ]] && jq -e '.plugin_migrated == true' "$MANIFEST_PATH" &>/dev/null; then
    return 1
  fi

  return 0
}

# run_plugin_migration()
# Removes template-managed skills, commands, hooks, and validate-bash.sh
# from the downstream repo and updates settings.json for the plugin system.
#
# Side effects:
#   - Deletes known template-managed files from .claude/
#   - Updates .claude/settings.json: removes hooks, adds marketplace/plugin config
#   - Sets plugin_migrated=true in template-state.json
#   - Appends removed files to DELETED_FILES array
#   - Logs all actions
run_plugin_migration() {
  log_step "Migrating to kk plugin system"

  local upstream_repo
  upstream_repo=$(get_manifest_value '.upstream_repo')

  # Static list of known template-managed files to remove.
  # These are the pre-plugin skill/command names that were installed by
  # template-sync before v0.5.0. Downstream projects upgrading from pre-plugin
  # versions need these paths cleaned up so the plugin can take over.
  # Do NOT rename these to post-plugin skill names — historical names only.
  local dirs_to_remove=(
    ".claude/skills/analysis-process"
    ".claude/skills/cove"
    ".claude/skills/development-guidelines"
    ".claude/skills/documentation-process"
    ".claude/skills/implementation-process"
    ".claude/skills/implementation-review"
    ".claude/skills/merge-docs"
    ".claude/skills/solid-code-review"
    ".claude/skills/testing-process"
    ".claude/commands/cove"
    ".claude/commands/implementation-review"
    ".claude/commands/migrate-from-taskmaster"
    ".claude/commands/sync-workflow"
  )
  local files_to_remove=(
    ".claude/scripts/validate-bash.sh"
  )

  # Track and optionally remove directories
  for dir in "${dirs_to_remove[@]}"; do
    if [[ -d "$dir" ]]; then
      DELETED_FILES+=("$dir/")
      if $APPLY_MODE; then
        rm -rf "$dir"
        log_info "Removed $dir/"
      else
        log_info "Would remove $dir/"
      fi
    fi
  done

  # Track and optionally remove individual files
  for file in "${files_to_remove[@]}"; do
    if [[ -f "$file" ]]; then
      DELETED_FILES+=("$file")
      if $APPLY_MODE; then
        rm -f "$file"
        log_info "Removed $file"
      else
        log_info "Would remove $file"
      fi
    fi
  done

  if $APPLY_MODE; then
    # Clean up empty parent directories
    rmdir .claude/skills 2>/dev/null || true
    rmdir .claude/commands 2>/dev/null || true

    # Update settings.json: remove hooks, add marketplace and plugin config
    local settings_file=".claude/settings.json"
    if [[ -f "$settings_file" ]]; then
      if json_update "$settings_file" \
        'del(.hooks) |
         .extraKnownMarketplaces = {
           "claude-toolbox": {
             "source": {
               "source": "github",
               "repo": $repo
             }
           }
         }' --arg repo "$upstream_repo"; then
        log_info "Updated $settings_file for plugin system"
      else
        log_warn "Failed to update $settings_file — manual update required"
      fi
    fi

    # Set plugin_migrated flag in manifest
    if json_update "$MANIFEST_PATH" '.plugin_migrated = true'; then
      log_info "Set plugin_migrated flag in manifest"
    else
      log_warn "Failed to update manifest — manual update required"
    fi
  fi

  log_success "Plugin migration complete"
}

# =============================================================================
# Serena Removal Migration
# =============================================================================

# needs_serena_removal()
# Checks whether the downstream repo still has Serena artifacts from the
# template defaults that should be cleaned up.
# Detection is based on the SERENA_INITIAL_PROMPT manifest variable — its
# presence means the repo was set up when Serena was a template default.
# If a user manually added .serena/ without the variable, we leave it alone.
#
# Returns:
#   0 if removal is needed
#   1 if not needed
needs_serena_removal() {
  if [[ -f "$MANIFEST_PATH" ]] && jq -e '.variables.SERENA_INITIAL_PROMPT' "$MANIFEST_PATH" &>/dev/null 2>&1; then
    return 0
  fi

  return 1
}

# run_serena_removal()
# Removes .serena/ directory and SERENA_INITIAL_PROMPT from the manifest.
# Serena MCP was dropped from upstream defaults — downstream repos no longer
# need the config directory or the manifest variable.
#
# Side effects:
#   - Deletes .serena/ directory if present
#   - Removes SERENA_INITIAL_PROMPT from manifest variables
#   - Appends removed files to DELETED_FILES array
run_serena_removal() {
  log_step "Removing Serena artifacts (no longer in upstream defaults)"

  # Respect sync_exclusions — if .serena/ is excluded, skip directory removal
  if is_excluded ".serena/project.yml"; then
    log_info "Skipping .serena/ removal (matched sync_exclusions)"
  else
    if [[ -d ".serena" ]]; then
      DELETED_FILES+=(".serena/")
      if $APPLY_MODE; then
        rm -rf ".serena"
        log_info "Removed .serena/"
      else
        log_info "Would remove .serena/"
      fi
    fi

    # Remove !.serena from .gitignore if present
    if [[ -f ".gitignore" ]] && grep -q '^!\.serena' .gitignore; then
      MODIFIED_FILES+=(".gitignore")
      if $APPLY_MODE; then
        sed -i '/^!\.serena$/d' .gitignore
        log_info "Removed !.serena from .gitignore"
      else
        log_info "Would remove !.serena from .gitignore"
      fi
    fi

    # Remove mcp__serena__* from settings.local.json permissions if present
    local local_settings=".claude/settings.local.json"
    if [[ -f "$local_settings" ]] && jq -e '.permissions.allow | any(startswith("mcp__serena__"))' "$local_settings" &>/dev/null 2>&1; then
      if $APPLY_MODE; then
        if json_update "$local_settings" '.permissions.allow |= map(select(startswith("mcp__serena__") | not))'; then
          MODIFIED_FILES+=("$local_settings")
          log_info "Removed mcp__serena__* from $local_settings permissions"
        fi
      else
        MODIFIED_FILES+=("$local_settings")
        log_info "Would remove mcp__serena__* from $local_settings permissions"
      fi
    fi
  fi

  # Clean manifest: remove SERENA_INITIAL_PROMPT variable and set flag
  if [[ -f "$MANIFEST_PATH" ]]; then
    if $APPLY_MODE; then
      if json_update "$MANIFEST_PATH" 'del(.variables.SERENA_INITIAL_PROMPT)'; then
        read_manifest
        log_info "Removed SERENA_INITIAL_PROMPT from manifest"
      else
        log_warn "Failed to update manifest — manual cleanup may be needed"
      fi
    else
      log_info "Would remove SERENA_INITIAL_PROMPT from manifest"
    fi
  fi

  log_success "Serena removal complete"
}

# =============================================================================
# Script Consolidation Migration
# =============================================================================

# needs_script_consolidation()
# Checks whether scripts still exist at pre-consolidation locations.
# Returns 0 if any old-location scripts are found.
needs_script_consolidation() {
  local old_paths=(
    ".github/scripts/template-sync.sh"
    ".github/scripts/semver-compare.sh"
    ".claude/scripts/statusline.sh"
    ".claude/scripts/statusline_enhanced.sh"
    ".claude/scripts/sync-workflow.sh"
    "docs/update.sh"
  )

  for path in "${old_paths[@]}"; do
    if [[ -f "$path" ]]; then
      return 0
    fi
  done

  return 1
}

# run_script_consolidation()
# Removes scripts from pre-consolidation locations. The new copies at
# .claude/toolbox/scripts/ arrive via the normal .claude/ directory sync.
#
# Side effects:
#   - Deletes old-location script files
#   - Removes empty parent directories
#   - Appends removed files to DELETED_FILES array
run_script_consolidation() {
  log_step "Consolidating scripts to .claude/toolbox/scripts/"

  local files_to_remove=(
    ".github/scripts/template-sync.sh"
    ".github/scripts/semver-compare.sh"
    ".claude/scripts/statusline.sh"
    ".claude/scripts/statusline_enhanced.sh"
    ".claude/scripts/sync-workflow.sh"
    "docs/update.sh"
  )

  for file in "${files_to_remove[@]}"; do
    if [[ -f "$file" ]]; then
      DELETED_FILES+=("$file")
      if $APPLY_MODE; then
        rm -f "$file"
        log_info "Removed $file"
      else
        log_info "Would remove $file"
      fi
    fi
  done

  log_success "Script consolidation complete"
}

# =============================================================================
# Version Resolution and Template Fetching
# =============================================================================

# resolve_version()
# Resolves target version string to a concrete git ref or SHA.
#
# Args:
#   $1 - Target version ("latest", "main", "master", "HEAD", or specific tag/SHA)
#   $2 - Upstream repository (owner/repo format)
#
# Returns:
#   Resolved version string via stdout:
#   - For "latest": returns tag name (e.g., "v1.0.0") or SHA if no tags
#   - For "main"/"master"/"HEAD": returns actual commit SHA
#   - For specific tag/SHA: returns as-is
#   Exits with 1 if resolution fails
#
# Note: All logging goes to stderr to keep stdout clean for return value
resolve_version() {
  local target="$1"
  local upstream="$2"
  local resolved=""
  local repo_url="https://github.com/$upstream.git"

  case "$target" in
  latest)
    # Find highest version tag using semver comparison (source: semver-compare.sh).
    # git's --sort=-v:refname is broken for pre-releases: it ranks v1.0.0-rc.1
    # above v1.0.0. We iterate all tags and compare properly instead.
    local tags
    tags=$(git ls-remote --tags "$repo_url" 2>/dev/null |
      { grep -v '\^{}' || true; } |
      sed 's/.*refs\/tags\///')

    local tag
    while IFS= read -r tag; do
      [[ -z "$tag" ]] && continue
      local stripped="${tag#v}"
      # Skip non-semver tags
      [[ "$stripped" =~ ^[0-9]+\.[0-9]+\.[0-9]+ ]] || continue
      if [[ -z "$resolved" ]]; then
        resolved="$tag"
      else
        local resolved_stripped="${resolved#v}"
        if [[ "$(compare "$stripped" "$resolved_stripped")" == "gt" ]]; then
          resolved="$tag"
        fi
      fi
    done <<< "$tags"

    # If no tags exist, resolve default branch to SHA
    if [[ -z "$resolved" ]]; then
      log_warn "No tags found in upstream repository, using default branch" >&2
      resolved=$(git ls-remote "$repo_url" HEAD 2>/dev/null | cut -f1)
      if [[ -z "$resolved" ]]; then
        log_error "Failed to resolve default branch SHA for upstream" >&2
        exit 1
      fi
    fi
    ;;
  main | master)
    # Resolve branch name to actual commit SHA
    resolved=$(git ls-remote "$repo_url" "refs/heads/$target" 2>/dev/null | cut -f1)
    if [[ -z "$resolved" ]]; then
      log_error "Branch '$target' not found in upstream repository" >&2
      exit 1
    fi
    ;;
  HEAD)
    # Resolve HEAD (default branch) to actual commit SHA
    resolved=$(git ls-remote "$repo_url" HEAD 2>/dev/null | cut -f1)
    if [[ -z "$resolved" ]]; then
      log_error "Failed to resolve HEAD for upstream repository" >&2
      exit 1
    fi
    ;;
  *)
    # Assume specific tag or SHA - return as-is
    resolved="$target"
    ;;
  esac

  # Validate we got something
  if [[ -z "$resolved" ]]; then
    log_error "Failed to resolve version: $target"
    exit 1
  fi

  echo "$resolved"
}

# fetch_upstream_templates()
# Fetches templates from upstream repository using git sparse-checkout.
# Implements retry logic for network failures (3 attempts, 5s delay).
#
# Args:
#   $1 - Version to fetch (tag, branch, or SHA)
#   $2 - Upstream repository (owner/repo format)
#   $3 - Working directory for clone operation
#
# Returns:
#   0 on success
#   Exits with 1 if fetch fails after retries or templates not found
#
# Side effects:
#   Sets global FETCHED_TEMPLATES_PATH to the path of fetched templates
#   Creates directories in work_dir
#   Logs progress/error messages
fetch_upstream_templates() {
  local version="$1"
  local upstream="$2"
  local work_dir="$3"
  local repo_url="https://github.com/$upstream.git"

  # Retry configuration
  local max_retries=3
  local retry_delay=5
  local attempt

  log_step "Fetching templates from $upstream @ $version"

  # Create work directory
  mkdir -p "$work_dir"

  # Clone with blob filter for efficiency (with retry logic)
  for ((attempt = 1; attempt <= max_retries; attempt++)); do
    if git clone --depth 1 --filter=blob:none \
      "$repo_url" "$work_dir/upstream" --quiet 2>/dev/null; then
      break
    fi

    if ((attempt < max_retries)); then
      log_warn "Clone failed, retrying in ${retry_delay}s (attempt $attempt/$max_retries)"
      sleep "$retry_delay"
      rm -rf "$work_dir/upstream" 2>/dev/null || true
    else
      log_error "Failed to fetch upstream after $max_retries attempts"
      log_error "Unable to reach GitHub. Please check your network connection and try again."
      log_error "Repository URL: $repo_url"
      exit 1
    fi
  done

  cd "$work_dir/upstream"

  # For non-default branches/tags, we need to fetch explicitly since we used --depth 1
  # HEAD means use whatever was cloned (default branch)
  local current_branch
  current_branch=$(git rev-parse --abbrev-ref HEAD 2>/dev/null || echo "")

  if [[ "$version" != "HEAD" && "$version" != "$current_branch" ]]; then
    # Fetch the specific version (try branch first, then tag)
    if ! git fetch --depth 1 origin "$version" --quiet 2>/dev/null; then
      # Try as a tag
      if ! git fetch --depth 1 origin "refs/tags/$version:refs/tags/$version" --quiet 2>/dev/null; then
        log_error "Invalid version: $version"
        log_error "The specified version does not exist in the upstream repository."
        log_error "Use 'latest' for the most recent release, 'main' for bleeding edge,"
        log_error "or specify a valid tag like 'v1.0.0'."
        cd - >/dev/null
        exit 1
      fi
    fi

    # Checkout the fetched version (try branch, then tag, then FETCH_HEAD)
    if ! git checkout "$version" --quiet 2>/dev/null; then
      if ! git checkout "tags/$version" --quiet 2>/dev/null; then
        if ! git checkout FETCH_HEAD --quiet 2>/dev/null; then
          log_error "Failed to checkout version: $version"
          log_error "The version was fetched but checkout failed unexpectedly."
          cd - >/dev/null
          exit 1
        fi
      fi
    fi
  fi

  # Configure sparse-checkout to fetch config dirs and sync infrastructure
  if ! git sparse-checkout init --cone --quiet 2>/dev/null; then
    log_warn "Sparse-checkout init failed, continuing with full checkout"
  fi
  if ! git sparse-checkout set .claude .codex .github/workflows/template-sync.yml klaude-plugin/.claude-plugin/plugin.json --quiet 2>/dev/null; then
    log_warn "Sparse-checkout set failed, config dirs may not exist at this version"
  fi

  cd - >/dev/null

  # Build a staging structure with claude/ subdirs (without dot prefix)
  # so the downstream substitution and comparison pipeline works unchanged.
  FETCHED_TEMPLATES_PATH="$work_dir/fetched"
  mkdir -p "$FETCHED_TEMPLATES_PATH"

  local upstream_root="$work_dir/upstream"
  if [[ -d "$upstream_root/.claude" ]]; then
    cp -rp "$upstream_root/.claude" "$FETCHED_TEMPLATES_PATH/claude"
    # settings.local.json is per-repo and must never be synced downstream
    rm -f "$FETCHED_TEMPLATES_PATH/claude/settings.local.json"
    # capy-related configs and files are generated by capy tooling per-repo and must never be synced downstream
    rm -rf "$FETCHED_TEMPLATES_PATH/claude/capy"
    rm -f "$FETCHED_TEMPLATES_PATH/claude/scripts/capy.sh"
  fi
  if [[ -d "$upstream_root/.codex" ]]; then
    cp -rp "$upstream_root/.codex" "$FETCHED_TEMPLATES_PATH/codex"
    rm -f "$FETCHED_TEMPLATES_PATH/codex/scripts/capy.sh"
  fi
  if [[ ! -d "$FETCHED_TEMPLATES_PATH/claude" ]]; then
    log_error "Config directories not found in upstream at version: $version"
    log_error "Expected .claude/ in the upstream repository."
    log_error "The upstream repository may not have this directory at this version,"
    log_error "or the repository structure has changed."
    exit 1
  fi

  log_success "Fetched config from $upstream @ $version"
}

# =============================================================================
# Substitution Functions
# =============================================================================

# apply_substitutions()
# Applies project-specific variable substitutions to fetched template files.
# Mirrors the substitution logic from template-cleanup.sh for consistency.
#
# Args:
#   $1 - Source template directory (raw fetched templates)
#   $2 - Output directory for substituted templates
#
# Returns:
#   0 on success
#
# Substitutions applied:
#   - Claude Code settings: CC_MODEL, CC_EFFORT_LEVEL, CC_PERMISSION_MODE
#
# Side effects:
#   Creates output directory and copies/modifies template files
#   Logs progress messages
apply_substitutions() {
  local template_dir="$1"
  local output_dir="$2"

  log_step "Applying substitutions from manifest"

  # Copy templates to output directory (preserving permissions)
  mkdir -p "$output_dir"
  cp -rp "$template_dir"/* "$output_dir/"

  # Read all variables from manifest
  local project_name languages cc_model cc_effort_level cc_permission_mode cc_statusline

  project_name=$(get_manifest_value '.variables.PROJECT_NAME')
  languages=$(get_manifest_value '.variables.LANGUAGES')
  cc_model=$(get_manifest_value '.variables.CC_MODEL')
  cc_effort_level=$(get_manifest_value '.variables.CC_EFFORT_LEVEL // "high"')
  cc_permission_mode=$(get_manifest_value '.variables.CC_PERMISSION_MODE // "default"')
  cc_statusline=$(get_manifest_value '.variables.CC_STATUSLINE // "enhanced"')

  # --- Claude Code Settings (claude/settings.json) ---
  local cc_settings_file="$output_dir/claude/settings.json"
  if [[ -f "$cc_settings_file" ]]; then
    # Smart-merge upstream template into downstream's settings.json.
    # Downstream is "master": existing values are never overwritten.
    # Upstream fills gaps: new keys/array entries are added.
    # Falls back to the upstream template copy for first-time sync.
    local downstream_settings=".claude/settings.json"
    if [[ -f "$downstream_settings" ]]; then
      # $cc_settings_file contains the upstream template copy at this point.
      # Read downstream as jq input, upstream via --slurpfile.
      if jq --slurpfile upstream "$cc_settings_file" '
        def smart_merge($u):
          if (type == "object") and ($u | type == "object") then
            reduce ($u | keys[]) as $k (.;
              if has($k) then .[$k] = (.[$k] | smart_merge($u[$k]))
              else .[$k] = $u[$k] end)
          elif (type == "array") and ($u | type == "array") then
            . as $d | . + [$u[] | select(. as $i | $d | index($i) | not)]
          else . end;
        smart_merge($upstream[0])
      ' "$downstream_settings" >"${cc_settings_file}.tmp"; then
        mv "${cc_settings_file}.tmp" "$cc_settings_file"
      else
        log_warn "Smart merge failed — downstream .claude/settings.json may contain invalid JSON"
        log_warn "Falling back to upstream template for settings.json"
        rm -f "${cc_settings_file}.tmp"
      fi
    fi

    local statusline_script="statusline_enhanced.sh"
    if [[ "$cc_statusline" == "basic" ]]; then
      statusline_script="statusline.sh"
    fi
    local upstream_repo
    upstream_repo=$(get_manifest_value '.upstream_repo')
    jq \
      --arg cc_model "$cc_model" \
      --arg cc_effort_level "$cc_effort_level" \
      --arg cc_permission_mode "$cc_permission_mode" \
      --arg statusline_script "$statusline_script" \
      --arg repo "$upstream_repo" \
      '
      # Model: "default" removes the key, otherwise set it
      if $cc_model == "default" then del(.model) else .model = $cc_model end |
      # Effort level: "default" removes the key, otherwise set it
      if $cc_effort_level == "default" then del(.effortLevel) else .effortLevel = $cc_effort_level end |
      # Permission mode
      .permissions.defaultMode = $cc_permission_mode |
      # Statusline script (guard against null/missing statusLine)
      # Migrate old path (.claude/scripts/) to new (.claude/toolbox/scripts/)
      # and swap script filename if needed
      (if (.statusLine.command | type) == "string" then
        .statusLine.command |= gsub("\\.claude/scripts/"; ".claude/toolbox/scripts/") |
        .statusLine.command |= gsub("statusline_enhanced\\.sh"; $statusline_script)
      else . end) |
      # Plugin marketplace: directory -> github source for downstream
      .extraKnownMarketplaces."claude-toolbox".source = { "source": "github", "repo": $repo }
      ' "$cc_settings_file" >"${cc_settings_file}.tmp" && mv "${cc_settings_file}.tmp" "$cc_settings_file"

    log_info "Applied Claude Code settings"
  fi

  # --- Codex Settings (codex/config.toml) ---
  local codex_config_file="$output_dir/codex/config.toml"
  if [[ -f "$codex_config_file" ]]; then
    local codex_model codex_approval_policy
    codex_model=$(get_manifest_value '.variables.CODEX_MODEL // "gpt-5.6-sol"')
    codex_approval_policy=$(get_manifest_value '.variables.CODEX_APPROVAL_POLICY // "on-request"')

    yq -i -p toml -o toml \
      ".model = \"$codex_model\" | .approval_policy = \"$codex_approval_policy\"" \
      "$codex_config_file"

    local skip_capy
    skip_capy=$(get_manifest_value '.variables.SKIP_CAPY // "false"')
    if [[ "$skip_capy" == "true" ]]; then
      yq -i -p toml -o toml 'del(.mcp_servers.capy) | with(select(.mcp_servers | length == 0); del(.mcp_servers))' "$codex_config_file"
      log_info "Stripped capy MCP server config (SKIP_CAPY=true)"
    fi

    log_info "Applied Codex config.toml settings"
  fi

  log_success "Substitutions applied to $output_dir"
}

# copy_sync_files()
# Copies sync infrastructure files (workflow) from upstream to staging.
# These files are synced as-is without variable substitution.
# Note: template-sync.sh and other scripts are now under .claude/toolbox/scripts/
# and are synced as part of the .claude/ directory — no separate copy needed.
#
# Args:
#   $1 - Upstream directory (parent of .github/)
#   $2 - Output directory for staged files
#
# Returns:
#   0 on success
#
# Side effects:
#   Creates workflows/ subdirectory in output_dir
#   Copies template-sync.yml if it exists
copy_sync_files() {
  local upstream_dir="$1"
  local output_dir="$2"

  log_step "Copying sync infrastructure files"

  mkdir -p "$output_dir/workflows"

  local copied=0

  # Copy workflow if it exists
  if [[ -f "$upstream_dir/.github/workflows/template-sync.yml" ]]; then
    cp "$upstream_dir/.github/workflows/template-sync.yml" "$output_dir/workflows/"
    log_info "Copied template-sync.yml"
    copied=$((copied + 1))
  fi

  if ((copied > 0)); then
    log_success "Copied $copied sync infrastructure file(s)"
  else
    log_info "No sync infrastructure files found in upstream"
  fi
}

# =============================================================================
# File Comparison Functions
# =============================================================================

# compare_files()
# Compares staging directory against current project directories.
# Detects added, modified, deleted, and unchanged files.
#
# Args:
#   $1 - Staging directory containing substituted templates
#
# Returns:
#   0 on success
#
# Side effects:
#   Populates global arrays: ADDED_FILES, MODIFIED_FILES, DELETED_FILES, UNCHANGED_FILES
#   Logs comparison summary
#
# Directories compared:
#   staging/claude    -> .claude/
compare_files() {
  local staging_dir="$1"

  log_step "Comparing files with current project"

  # Reset arrays
  ADDED_FILES=()
  MODIFIED_FILES=()
  DELETED_FILES=()
  UNCHANGED_FILES=()
  EXCLUDED_FILES=()

  # Directories to compare (staging subdir -> project dir)
  local -A dir_map=(
    ["claude"]=".claude"
    ["codex"]=".codex"
    ["workflows"]=".github/workflows"
  )

  for staging_subdir in "${!dir_map[@]}"; do
    local project_dir="${dir_map[$staging_subdir]}"
    local staging_path="$staging_dir/$staging_subdir"

    # Skip if staging subdir doesn't exist
    [[ ! -d "$staging_path" ]] && continue

    local staging_find_args=("$staging_path" -type f -print0)

    # Find all files in staging (excluding user-scoped directories)
    while IFS= read -r -d '' staging_file; do
      local relative_path="${staging_file#$staging_path/}"
      local project_file="$project_dir/$relative_path"
      local display_path="$project_dir/$relative_path"

      # Check exclusion before categorization
      if is_excluded "$display_path"; then
        EXCLUDED_FILES+=("$display_path")
        continue
      fi

      if [[ ! -f "$project_file" ]]; then
        # File exists in staging but not in project -> Added
        ADDED_FILES+=("$display_path")
      elif ! diff -q "$staging_file" "$project_file" &>/dev/null; then
        # Files differ -> Modified
        MODIFIED_FILES+=("$display_path")
      else
        # Files are identical -> Unchanged
        UNCHANGED_FILES+=("$display_path")
      fi
    done < <(find "${staging_find_args[@]}" 2>/dev/null)

    # Find deleted files (exist in project but not in staging)
    # Skip for workflows/ — we only sync template-sync.yml, not the entire directory
    if [[ -d "$project_dir" && "$staging_subdir" != "workflows" ]]; then
      local find_args=("$project_dir" -type f -print0)

      while IFS= read -r -d '' project_file; do
        local relative_path="${project_file#$project_dir/}"
        local staging_file="$staging_path/$relative_path"
        local display_path="$project_dir/$relative_path"

        # Skip excluded files in deletion detection (don't add to EXCLUDED_FILES to avoid double-counting)
        if is_excluded "$display_path"; then
          continue
        fi

        if [[ ! -f "$staging_file" ]]; then
          # File exists in project but not in staging -> Deleted
          DELETED_FILES+=("$display_path")
        fi
      done < <(find "${find_args[@]}" 2>/dev/null)
    fi
  done

  log_success "Comparison complete: ${#ADDED_FILES[@]} added, ${#MODIFIED_FILES[@]} modified, ${#DELETED_FILES[@]} deleted, ${#UNCHANGED_FILES[@]} unchanged, ${#EXCLUDED_FILES[@]} excluded"
}

# generate_diff_report()
# Generates a human-readable diff report showing all changes.
# In CI mode, also outputs GitHub Actions compatible format.
#
# Args:
#   $1 - Staging directory containing substituted templates
#
# Returns:
#   0 on success
#
# Output:
#   - Human-readable report to stdout with colored output
#   - In CI mode: writes to GITHUB_OUTPUT file for workflow consumption
#   - Shows version transition, change summary, and file diffs
#
# Side effects:
#   Reads from global arrays (ADDED_FILES, MODIFIED_FILES, etc.)
#   Reads RESOLVED_VERSION global variable
generate_diff_report() {
  local staging_dir="$1"
  local total_changes=$((${#ADDED_FILES[@]} + ${#MODIFIED_FILES[@]} + ${#DELETED_FILES[@]}))
  local has_changes=false
  [[ $total_changes -gt 0 ]] && has_changes=true

  # CI mode: output GitHub Actions format
  if $CI_MODE; then
    if [[ -n "${GITHUB_OUTPUT:-}" ]]; then
      # Write to GITHUB_OUTPUT file
      {
        echo "has_changes=$has_changes"
        echo "added_count=${#ADDED_FILES[@]}"
        echo "modified_count=${#MODIFIED_FILES[@]}"
        echo "deleted_count=${#DELETED_FILES[@]}"
        echo "unchanged_count=${#UNCHANGED_FILES[@]}"
        echo "excluded_count=${#EXCLUDED_FILES[@]}"
        echo "total_changes=$total_changes"
        echo "resolved_version=$RESOLVED_VERSION"
        echo "plugin_migrated=$PLUGIN_MIGRATED"
        echo "diff_summary<<EOF"
        generate_markdown_summary "$staging_dir"
        echo "EOF"
      } >>"$GITHUB_OUTPUT"
    else
      # Output to stdout for local testing
      echo "::group::GitHub Actions Outputs"
      echo "has_changes=$has_changes"
      echo "added_count=${#ADDED_FILES[@]}"
      echo "modified_count=${#MODIFIED_FILES[@]}"
      echo "deleted_count=${#DELETED_FILES[@]}"
      echo "unchanged_count=${#UNCHANGED_FILES[@]}"
      echo "excluded_count=${#EXCLUDED_FILES[@]}"
      echo "total_changes=$total_changes"
      echo "resolved_version=$RESOLVED_VERSION"
      echo "::endgroup::"
    fi
  fi

  # Human-readable output
  echo ""
  echo -e "${BOLD}═══════════════════════════════════════════════════════════════${NC}"
  echo -e "${BOLD}                    Template Sync Report                        ${NC}"
  echo -e "${BOLD}═══════════════════════════════════════════════════════════════${NC}"
  echo ""

  local current_version
  current_version=$(get_manifest_value '.template_version')
  echo -e "  ${CYAN}From:${NC} $current_version"
  echo -e "  ${CYAN}To:${NC}   $RESOLVED_VERSION"
  echo ""

  if ! $has_changes; then
    echo -e "  ${GREEN}No changes detected - templates are up to date${NC}"
    echo ""
    return
  fi

  echo -e "  ${CYAN}Summary:${NC}"
  echo -e "    Added:     ${GREEN}${#ADDED_FILES[@]}${NC}"
  echo -e "    Modified:  ${YELLOW}${#MODIFIED_FILES[@]}${NC}"
  echo -e "    Deleted:   ${RED}${#DELETED_FILES[@]}${NC}"
  echo -e "    Unchanged: ${#UNCHANGED_FILES[@]}"
  echo -e "    Excluded:  ${#EXCLUDED_FILES[@]}"
  echo ""

  # List added files
  if [[ ${#ADDED_FILES[@]} -gt 0 ]]; then
    echo -e "  ${GREEN}Added files:${NC}"
    for file in "${ADDED_FILES[@]}"; do
      echo -e "    ${GREEN}+${NC} $file"
    done
    echo ""
  fi

  # List modified files with inline diffs
  if [[ ${#MODIFIED_FILES[@]} -gt 0 ]]; then
    echo -e "  ${YELLOW}Modified files:${NC}"
    for file in "${MODIFIED_FILES[@]}"; do
      echo -e "    ${YELLOW}~${NC} $file"
    done
    echo ""

    # Show diffs for modified files (limited to first 20 lines each)
    if ! $CI_MODE; then
      echo -e "  ${CYAN}Diffs:${NC}"
      for file in "${MODIFIED_FILES[@]}"; do
        local staging_file
        # Map project path back to staging path
        if [[ "$file" == ".claude/"* ]]; then
          staging_file="$staging_dir/claude/${file#.claude/}"
        else
          staging_file="$staging_dir/$file"
        fi

        if [[ -f "$staging_file" && -f "$file" ]]; then
          echo ""
          echo -e "    ${BOLD}--- $file${NC}"
          diff -u "$file" "$staging_file" 2>/dev/null | head -30 | sed 's/^/    /' || true
        fi
      done
      echo ""
    fi
  fi

  # List deleted files
  if [[ ${#DELETED_FILES[@]} -gt 0 ]]; then
    echo -e "  ${RED}Deleted files:${NC}"
    for file in "${DELETED_FILES[@]}"; do
      echo -e "    ${RED}-${NC} $file"
    done
    echo ""
  fi

  # List excluded files
  if [[ ${#EXCLUDED_FILES[@]} -gt 0 ]]; then
    echo -e "  ${CYAN}Excluded files (via sync_exclusions):${NC}"
    for file in "${EXCLUDED_FILES[@]}"; do
      echo -e "    ${CYAN}○${NC} $file"
    done
    echo ""
  fi

  echo -e "${BOLD}═══════════════════════════════════════════════════════════════${NC}"
  echo ""

  if $DRY_RUN; then
    echo -e "  ${YELLOW}Dry run mode - no changes applied${NC}"
    echo -e "  Run without --dry-run to apply these changes"
    echo ""
  fi
}

# Generate markdown summary for PR body
generate_markdown_summary() {
  local staging_dir="$1"
  local current_version
  current_version=$(get_manifest_value '.template_version')

  echo "## Template Sync Summary"
  echo ""
  echo "**From:** \`$current_version\`"
  echo "**To:** \`$RESOLVED_VERSION\`"
  echo ""
  echo "### Changes"
  echo ""
  echo "| Type | Count |"
  echo "|------|-------|"
  echo "| Added | ${#ADDED_FILES[@]} |"
  echo "| Modified | ${#MODIFIED_FILES[@]} |"
  echo "| Deleted | ${#DELETED_FILES[@]} |"
  echo "| Excluded | ${#EXCLUDED_FILES[@]} |"
  echo ""

  if [[ ${#ADDED_FILES[@]} -gt 0 ]]; then
    echo "### Added Files"
    echo ""
    for file in "${ADDED_FILES[@]}"; do
      echo "- \`$file\`"
    done
    echo ""
  fi

  if [[ ${#MODIFIED_FILES[@]} -gt 0 ]]; then
    echo "### Modified Files"
    echo ""
    for file in "${MODIFIED_FILES[@]}"; do
      echo "- \`$file\`"
    done
    echo ""
  fi

  if [[ ${#DELETED_FILES[@]} -gt 0 ]]; then
    echo "### Deleted Files"
    echo ""
    for file in "${DELETED_FILES[@]}"; do
      echo "- \`$file\`"
    done
    echo ""
  fi

  if [[ ${#EXCLUDED_FILES[@]} -gt 0 ]]; then
    echo "### Excluded Files"
    echo ""
    echo "_These files were skipped due to \`sync_exclusions\` patterns in the manifest:_"
    echo ""
    for file in "${EXCLUDED_FILES[@]}"; do
      echo "- \`$file\`"
    done
    echo ""
  fi

  if $PLUGIN_MIGRATED; then
    echo "### Plugin Migration"
    echo ""
    echo "Skills, commands, and hooks have been migrated to the **kk** plugin."
    echo "Template-managed files listed under \"Deleted Files\" above were removed."
    echo ""
    echo "**After merging this PR:**"
    echo "1. Run \`/plugin install kk@claude-toolbox\` to install the plugin"
    echo "2. Commands are now namespaced: \`/project:command\` → \`/kk:dir:command\` (skills remain unprefixed)"
    echo ""
  fi

  local serena_deleted=false
  for file in "${DELETED_FILES[@]}"; do
    if [[ "$file" == ".serena/"* || "$file" == ".serena/" ]]; then
      serena_deleted=true
      break
    fi
  done
  if $serena_deleted; then
    echo "### Serena Removed"
    echo ""
    echo "Serena MCP has been removed from the template defaults."
    echo "The \`.serena/\` directory and \`SERENA_INITIAL_PROMPT\` manifest variable have been cleaned up."
    echo ""
    echo "If you still want to use Serena, configure it at the user level in \`~/.claude.json\`."
    echo ""
  fi
}

# =============================================================================
# Apply Changes (consolidates all mutations the YAML used to do inline)
# =============================================================================

# apply_changes()
# Applies all staged changes to the working tree: copies files, runs
# migrations, patches gitignore, auto-imports CLAUDE.extra.md, and
# updates the manifest version.
#
# Args:
#   $1 - Staging directory containing substituted templates
#   $2 - Resolved version string to write into the manifest
#
# Side effects:
#   Mutates working tree files, updates manifest
apply_changes() {
  local staging_dir="$1"
  local new_version="$2"

  log_step "Applying staged changes"

  # --- Copy staged files into working tree ---
  local -A dir_map=(
    ["claude"]=".claude"
    ["codex"]=".codex"
    ["workflows"]=".github/workflows"
    ["scripts"]=".github/scripts"
    ["docs"]="docs"
  )

  for staging_subdir in "${!dir_map[@]}"; do
    local source="$staging_dir/$staging_subdir"
    local target="${dir_map[$staging_subdir]}"
    [[ ! -d "$source" ]] && continue
    mkdir -p "$target"
    cp -r "$source/"* "$target/" 2>/dev/null || true
    log_info "Applied changes to $target/"
  done

  # Restore executable permissions on scripts
  chmod +x .codex/scripts/*.sh 2>/dev/null || true
  chmod +x .github/scripts/*.sh 2>/dev/null || true

  # --- Run migrations ---
  local upstream_dir
  upstream_dir="$(dirname "$staging_dir")/upstream"
  if needs_plugin_migration "$upstream_dir"; then
    run_plugin_migration
  fi

  if needs_serena_removal; then
    run_serena_removal
  fi

  if needs_script_consolidation; then
    run_script_consolidation
  fi

  # --- Patch .gitignore for .codex ---
  if [[ -d "$staging_dir/codex" && -f ".gitignore" ]]; then
    if git check-ignore -q .codex 2>/dev/null; then
      if ! grep -q '^!\.codex' .gitignore; then
        if grep -q '^!\.claude' .gitignore; then
          sed -i '/^!\.claude$/a !.codex' .gitignore
        else
          printf '!.codex\n' >> .gitignore
        fi
        log_info "Added !.codex to .gitignore (was excluded by .* pattern)"
      fi
    fi
  fi

  # --- Auto-import CLAUDE.extra.md ---
  if [[ -f "$staging_dir/claude/CLAUDE.extra.md" && -f "CLAUDE.md" ]]; then
    if ! grep -q '@.claude/CLAUDE.extra.md' CLAUDE.md; then
      printf '\n# Extra Instructions\n' >> CLAUDE.md
      printf '@.claude/CLAUDE.extra.md\n' >> CLAUDE.md
      log_info "Added @import reference for .claude/CLAUDE.extra.md to CLAUDE.md"
    fi
  fi

  # --- Update manifest version ---
  local synced_at
  synced_at=$(date -u +"%Y-%m-%dT%H:%M:%SZ")

  migrate_manifest
  backfill_manifest_variables

  if json_update "$MANIFEST_PATH" \
    '.template_version = $version | .synced_at = $synced_at' \
    --arg version "$new_version" --arg synced_at "$synced_at"; then
    log_info "Updated manifest to version $new_version"
  else
    log_warn "Failed to update manifest version"
  fi

  log_success "All changes applied"
}

# =============================================================================
# Help / Usage
# =============================================================================

show_help() {
  cat <<'EOF'
Template Sync Script
Synchronizes template updates from the upstream claude-toolbox repository.

Usage:
  ./template-sync.sh                    # Sync to latest version
  ./template-sync.sh [options]          # Sync with custom options

Options:
  --version VERSION     Target version to sync to
                        - "latest": Most recent tagged release (default)
                        - "main": Latest from main branch
                        - "v1.2.3": Specific tag
                        - SHA: Specific commit
  --dry-run             Preview changes without applying them
  --local               Fetch, compare, and apply in a single invocation
  --apply               Apply pre-staged changes to the working tree (CI only)
  --ci                  CI mode: outputs GitHub Actions compatible format
  --output-dir DIR      Directory to stage changes (default: temporary directory)
  -h, --help            Show this help message

Requires: jq, git, curl, yq (mikefarah/yq for YAML processing)

Exit Codes:
  0 - Success (changes found or no changes)
  1 - Operational error (missing manifest, network failure, invalid JSON)
  2 - Invalid CLI arguments

Examples:
  # Preview changes without applying
  ./template-sync.sh --dry-run

  # Sync to latest release and apply locally
  ./template-sync.sh --local

  # Sync to specific version and apply locally
  ./template-sync.sh --local --version v1.0.0

  # CI mode with custom output directory
  ./template-sync.sh --ci --output-dir ./staging
EOF
}

# =============================================================================
# CLI Argument Parsing
# =============================================================================

parse_arguments() {
  while [[ $# -gt 0 ]]; do
    case $1 in
    --version)
      if [[ -z "${2:-}" ]]; then
        log_error "--version requires a value"
        exit 2
      fi
      TARGET_VERSION="$2"
      shift 2
      ;;
    --dry-run)
      DRY_RUN=true
      shift
      ;;
    --apply)
      APPLY_MODE=true
      shift
      ;;
    --local)
      LOCAL_MODE=true
      shift
      ;;
    --ci)
      CI_MODE=true
      shift
      ;;
    --output-dir)
      if [[ -z "${2:-}" ]]; then
        log_error "--output-dir requires a value"
        exit 2
      fi
      STAGING_DIR="$2"
      shift 2
      ;;
    -h | --help)
      show_help
      exit 0
      ;;
    -*)
      log_error "Unknown option: $1"
      echo ""
      show_help
      exit 2
      ;;
    *)
      log_error "Unexpected argument: $1"
      echo ""
      show_help
      exit 2
      ;;
    esac
  done
}

# =============================================================================
# Main Entry Point
# =============================================================================

main() {
  # Register cleanup trap early for signal handling
  trap cleanup_on_exit EXIT INT TERM

  # Parse CLI arguments (before dependency check so APPLY_MODE is set)
  parse_arguments "$@"

  # Check dependencies (applies mode-aware checks)
  check_dependencies

  # Apply mode requires an explicit staging directory
  if $APPLY_MODE && [[ -z "$STAGING_DIR" ]]; then
    log_error "--apply requires --output-dir to locate staged changes"
    exit 2
  fi

  # Set default staging directory if not provided (non-apply mode only)
  if [[ -z "$STAGING_DIR" ]]; then
    STAGING_DIR=$(mktemp -d "/tmp/template-sync.XXXXXX")
    # Track temp directory for cleanup
    TEMP_DIR="$STAGING_DIR"
    if [[ ! -d "$STAGING_DIR" ]]; then
      log_error "Failed to create temporary directory"
      exit 1
    fi
  fi

  # Display configuration in non-CI mode
  if ! $CI_MODE; then
    echo ""
    echo -e "${BOLD}Template Sync${NC}"
    echo "  Target version: $TARGET_VERSION"
    echo "  Dry run:        $DRY_RUN"
    echo "  Staging dir:    $STAGING_DIR"
    echo ""
  fi

  # Read and validate manifest
  read_manifest
  validate_manifest

  # Apply mode: skip fetch/compare/report, just apply staged changes
  if $APPLY_MODE; then
    apply_changes "$STAGING_DIR/substituted" "$TARGET_VERSION"
    return 0
  fi

  migrate_manifest
  backfill_manifest_variables

  # Display manifest info
  if ! $CI_MODE; then
    local upstream_repo template_version project_name
    upstream_repo=$(get_manifest_value '.upstream_repo')
    template_version=$(get_manifest_value '.template_version')
    project_name=$(get_manifest_value '.variables.PROJECT_NAME')
    echo "  Upstream repo:  $upstream_repo"
    echo "  Current ver:    $template_version"
    echo "  Project name:   $project_name"
    echo ""
  fi

  # Get upstream repo from manifest
  local upstream_repo
  upstream_repo=$(get_manifest_value '.upstream_repo')

  # Resolve target version
  log_step "Resolving version: $TARGET_VERSION"
  RESOLVED_VERSION=$(resolve_version "$TARGET_VERSION" "$upstream_repo")
  log_info "Resolved version: $RESOLVED_VERSION"

  # Fetch upstream templates (sets FETCHED_TEMPLATES_PATH)
  fetch_upstream_templates "$RESOLVED_VERSION" "$upstream_repo" "$STAGING_DIR"

  # Display fetched templates info
  if ! $CI_MODE; then
    echo ""
    echo "  Templates path: $FETCHED_TEMPLATES_PATH"
    echo ""
  fi

  # Run plugin migration if needed (before comparing files)
  if needs_plugin_migration "$STAGING_DIR/upstream"; then
    PLUGIN_MIGRATED=true
    run_plugin_migration
  fi

  # Remove Serena artifacts if still present (upstream dropped Serena)
  if needs_serena_removal; then
    run_serena_removal
  fi

  if needs_script_consolidation; then
    run_script_consolidation
  fi

  # Apply substitutions to fetched templates
  SUBSTITUTED_TEMPLATES_PATH="$STAGING_DIR/substituted"
  apply_substitutions "$FETCHED_TEMPLATES_PATH" "$SUBSTITUTED_TEMPLATES_PATH"

  # Copy sync infrastructure files (no substitution needed)
  copy_sync_files "$STAGING_DIR/upstream" "$SUBSTITUTED_TEMPLATES_PATH"

  # Display substituted templates info
  if ! $CI_MODE; then
    echo ""
    echo "  Substituted to: $SUBSTITUTED_TEMPLATES_PATH"
    echo ""
  fi

  # Compare files and generate report
  # Save pre-compare deletions (from migrations, consolidation, etc.)
  # before compare_files resets arrays
  local pre_compare_deletions=("${DELETED_FILES[@]}")
  compare_files "$SUBSTITUTED_TEMPLATES_PATH"
  # Merge pre-compare deletions back, skipping any already found by compare
  if [[ ${#pre_compare_deletions[@]} -gt 0 ]]; then
    local -A compare_seen=()
    for f in "${DELETED_FILES[@]}"; do compare_seen["$f"]=1; done
    for f in "${pre_compare_deletions[@]}"; do
      [[ -z "${compare_seen[$f]+x}" ]] && DELETED_FILES+=("$f")
    done
  fi
  generate_diff_report "$SUBSTITUTED_TEMPLATES_PATH"

  # Summary
  local total_changes=$((${#ADDED_FILES[@]} + ${#MODIFIED_FILES[@]} + ${#DELETED_FILES[@]}))
  if [[ $total_changes -eq 0 ]]; then
    log_success "Templates are up to date - no changes needed"
  elif $DRY_RUN; then
    log_info "Dry run complete - $total_changes file(s) would be changed"
  elif $LOCAL_MODE; then
    log_info "Applying $total_changes change(s) locally..."
    APPLY_MODE=true
    apply_changes "$SUBSTITUTED_TEMPLATES_PATH" "$RESOLVED_VERSION"
  else
    log_info "Sync complete - $total_changes file(s) identified for update"
    log_info "Review the changes above and apply manually or via PR"
  fi
}

# Run main with all arguments only if script is executed directly (not sourced)
# This allows tests to source the file and access functions without running main()
if [[ "${BASH_SOURCE[0]:-}" == "${0:-}" ]]; then
  main "$@"
fi
