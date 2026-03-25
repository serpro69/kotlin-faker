#!/usr/bin/env bash
#
# Sync the template-sync workflow and script from the upstream template repository.
#
# Usage:
#   sync-workflow.sh [version]
#
# Version can be:
#   latest   - resolve the most recent git tag from upstream (default)
#   master   - use the master branch head
#   <tag>    - a specific tag, e.g. v0.3.0

set -euo pipefail

UPSTREAM_REPO="serpro69/claude-toolbox"
UPSTREAM_URL="https://github.com/$UPSTREAM_REPO"
RAW_URL="https://raw.githubusercontent.com/$UPSTREAM_REPO"

RED='\033[0;31m'
GREEN='\033[0;32m'
CYAN='\033[0;36m'
NC='\033[0m'

log_info()  { echo -e "${GREEN}[INFO]${NC} $1"; }
log_error() { echo -e "${RED}[ERROR]${NC} $1"; }
log_step()  { echo -e "${CYAN}>>>${NC} $1"; }

resolve_version() {
  local input="${1:-latest}"

  if [[ "$input" == "master" ]]; then
    echo "master"
    return
  fi

  if [[ "$input" != "latest" ]]; then
    echo "$input"
    return
  fi

  # Resolve latest tag
  local latest
  latest=$(git ls-remote --tags --sort=-v:refname "$UPSTREAM_URL.git" 2>/dev/null \
    | grep -v '\^{}' | head -1 | sed 's/.*refs\/tags\///' || true)

  if [[ -z "$latest" ]]; then
    log_error "Could not resolve latest tag, falling back to master"
    echo "master"
  else
    echo "$latest"
  fi
}

fetch_file() {
  local version="$1"
  local remote_path="$2"
  local local_path="$3"

  local url="$RAW_URL/$version/$remote_path"
  if ! curl -fsSL -H "Cache-Control: no-cache, no-store" "$url" -o "$local_path" 2>/dev/null; then
    log_error "Failed to fetch $url"
    echo "  Download manually: $UPSTREAM_URL/blob/$version/$remote_path"
    return 1
  fi
}

main() {
  local version
  version=$(resolve_version "${1:-latest}")
  log_step "Using version: $version"

  local failed=0

  log_step "Fetching template-sync.yml..."
  fetch_file "$version" ".github/workflows/template-sync.yml" ".github/workflows/template-sync.yml" || ((failed++))

  log_step "Fetching template-sync.sh..."
  fetch_file "$version" ".github/scripts/template-sync.sh" ".github/scripts/template-sync.sh" || ((failed++))
  chmod +x .github/scripts/template-sync.sh 2>/dev/null || true

  if [[ "$failed" -gt 0 ]]; then
    log_error "$failed file(s) failed to download"
    exit 1
  fi

  # Verify non-empty
  for f in .github/workflows/template-sync.yml .github/scripts/template-sync.sh; do
    if [[ ! -s "$f" ]]; then
      log_error "$f is empty after download"
      exit 1
    fi
  done

  log_info "Synced template-sync workflow and script to $version"

  # Show diff if in a git repo
  if git rev-parse --is-inside-work-tree &>/dev/null; then
    echo ""
    git diff --stat .github/workflows/template-sync.yml .github/scripts/template-sync.sh 2>/dev/null || true
  fi
}

main "$@"
