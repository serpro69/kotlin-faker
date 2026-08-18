#!/usr/bin/env bash
#
# Promotes a directory from docs/wip/ to docs/ and updates all references.
#

set -eu

usage() {
  cat <<EOF
Usage: $(basename "$0") <dirname>

Promotes a directory from docs/wip/ to docs/ by:
  1. Updating all file references from docs/wip/<dirname> to docs/<dirname>
  2. Moving the directory from docs/wip/<dirname> to docs/<dirname>

Arguments:
  dirname    Name of the directory in docs/wip/ to promote

Example:
  $(basename "$0") chain-of-verification
EOF
}

SED="sed"
XARGS="xargs"
if [ "$(uname)" = "Darwin" ]; then
  command -v gsed &>/dev/null && SED="gsed"
  command -v gxargs &>/dev/null && XARGS="gxargs"
fi

# Show help
if [[ "${1:-}" == "-h" || "${1:-}" == "--help" ]]; then
  usage
  exit 0
fi

# Check for required argument
if [[ -z "${1:-}" ]]; then
  echo "Error: Missing required argument <dirname>" >&2
  echo >&2
  usage >&2
  exit 1
fi

DIRNAME=$1

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/../../.." && pwd)"

# Validate source directory exists
if [[ ! -d "${PROJECT_ROOT}/docs/wip/${DIRNAME}" ]]; then
  echo "Error: Directory docs/wip/${DIRNAME} does not exist" >&2
  exit 1
fi

find "$PROJECT_ROOT" -type f -not -path "*/.git/*" -print0 | $XARGS -I{} -0 $SED -i "s/docs\/wip\/${DIRNAME}/docs\/${DIRNAME}/g" {}

mkdir -p docs/done

git -C "$PROJECT_ROOT" mv "docs/wip/${DIRNAME}" "docs/done/${DIRNAME}"

echo "Successfully promoted docs/wip/${DIRNAME} to docs/done/${DIRNAME}"
