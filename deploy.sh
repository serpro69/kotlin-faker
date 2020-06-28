#!/bin/bash
###
_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
if [[ "${_dir}" != "$(pwd)" ]]; then
    cd "${_dir}"
fi

# Set git author config
git config --local user.name "${GIT_USER_NAME}"
git config --local user.email "${GIT_USER_EMAIL}"

_tag="$(git describe --exact-match --tags HEAD 2>&1)"

if [[ "${_tag}" == *"no tag exactly matches"* ]]; then
    ./gradlew clean tag build cli-bot:nativeImage core:bintrayUpload -Prelease -PbintrayUser=${BINTRAY_USER} -PbintrayKey=${BINTRAY_KEY}
else
    if [[ "${_tag}" == *"No names found, cannot describe anything"* ]]; then
        echo "Setting tag for first release"
        git tag v0.0.1
    fi

    ./gradlew clean printVersion build cli-bot:nativeImage core:bintrayUpload -Prelease -PbintrayUser=${BINTRAY_USER} -PbintrayKey=${BINTRAY_KEY}
fi
