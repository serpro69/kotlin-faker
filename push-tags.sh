#!/usr/bin/env bash

git push "https://${GH_TOKEN}@github.com/${TRAVIS_REPO_SLUG}.git" --tags
