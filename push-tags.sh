#!/usr/bin/env bash
# Push git tags to remote
git push "https://${GH_TOKEN}@github.com/${TRAVIS_REPO_SLUG}.git" --tags
