#!/usr/bin/env bash
# Semver 2.0.0 compliant version comparison (https://semver.org/#spec-item-11)
# Usage: semver-compare.sh <version1> <version2>
# Output: "lt" (v1 < v2), "eq" (v1 == v2), "gt" (v1 > v2)

set -euo pipefail

compare() {
  local v1="${1%%+*}" v2="${2%%+*}" # strip build metadata (§10)

  local core1="${v1%%-*}" core2="${v2%%-*}"
  local pre1="" pre2=""
  [[ "$v1" == *-* ]] && pre1="${v1#*-}"
  [[ "$v2" == *-* ]] && pre2="${v2#*-}"

  IFS='.' read -r maj1 min1 pat1 <<< "$core1"
  IFS='.' read -r maj2 min2 pat2 <<< "$core2"

  if ((maj1 != maj2)); then ((maj1 < maj2)) && echo "lt" || echo "gt"; return; fi
  if ((min1 != min2)); then ((min1 < min2)) && echo "lt" || echo "gt"; return; fi
  if ((pat1 != pat2)); then ((pat1 < pat2)) && echo "lt" || echo "gt"; return; fi

  # §11.3: no pre-release > pre-release
  if [[ -z "$pre1" && -z "$pre2" ]]; then echo "eq"; return; fi
  if [[ -z "$pre1" ]]; then echo "gt"; return; fi
  if [[ -z "$pre2" ]]; then echo "lt"; return; fi

  # §11.4: compare dot-separated pre-release identifiers
  IFS='.' read -ra ids1 <<< "$pre1"
  IFS='.' read -ra ids2 <<< "$pre2"

  local i
  for ((i = 0; ; i++)); do
    if ((i >= ${#ids1[@]} && i >= ${#ids2[@]})); then echo "eq"; return; fi
    if ((i >= ${#ids1[@]})); then echo "lt"; return; fi # fewer fields → lower precedence
    if ((i >= ${#ids2[@]})); then echo "gt"; return; fi

    local a="${ids1[$i]}" b="${ids2[$i]}"
    local a_num=false b_num=false
    [[ "$a" =~ ^(0|[1-9][0-9]*)$ ]] && a_num=true
    [[ "$b" =~ ^(0|[1-9][0-9]*)$ ]] && b_num=true

    if $a_num && $b_num; then
      if ((10#$a != 10#$b)); then ((10#$a < 10#$b)) && echo "lt" || echo "gt"; return; fi
    elif $a_num; then
      echo "lt"; return # §11.4.3: numeric < alphanumeric
    elif $b_num; then
      echo "gt"; return
    else
      if [[ "$a" < "$b" ]]; then echo "lt"; return; fi
      if [[ "$a" > "$b" ]]; then echo "gt"; return; fi
    fi
  done
}

if [[ "${BASH_SOURCE[0]:-}" == "${0:-}" ]]; then
  if [[ $# -ne 2 ]]; then
    echo "Usage: $0 <version1> <version2>" >&2
    exit 1
  fi
  compare "$1" "$2"
fi
