#!/bin/bash
###
_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
if [[ "${_dir}" != "$(pwd)" ]]; then
    cd "${_dir}"
fi

_tag="$(git describe --exact-match --tags HEAD 2>&1)"

if [[ "${_tag}" == *"No names found, cannot describe anything"* ]] || [[ "${_tag}" == *"no tag exactly matches"* ]]; then
    echo 2>&1 "$_tag"
    exit 1
else
    ./gradlew clean printVersion build nativeCompile bintrayUpload -Prelease -PbintrayUser=${BINTRAY_USER} -PbintrayKey=${BINTRAY_KEY}
fi
