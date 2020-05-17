#!/bin/bash
_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
if [[ "$_dir" != "$(pwd)" ]]; then
    cd "$_dir"
fi

_tag="$(git describe --exact-match --tags HEAD 2>&1)"
if [[ "$_tag" == *"no tag exactly matches"* ]]; then
    ./gradlew clean tag build bintrayUpload -Prelease -PbintrayUser=${BINTRAY_USER} -PbintrayKey=${BINTRAY_KEY}
    git push "https://${GH_TOKEN}@github.com/${TRAVIS_REPO_SLUG}.git" --tags
else
    ./gradlew clean printVersion build bintrayUpload -Prelease -PbintrayUser=${BINTRAY_USER} -PbintrayKey=${BINTRAY_KEY}
fi
