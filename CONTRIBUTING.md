# Introduction

Thanks for your interest in contributing! This repository is free open source and as such dependent on your contributions. These guidelines should help you get started more quickly and should ensure a smooth contribution process for both those contributing and those reviewing contributions. Please read them thoroughly before contributing with a Pull Request, and at least skim them before adding an issue.

# How to ask for help

At the moment, it is totally fine to [open an issue](https://github.com/serpro69/kotlin-faker/issues/new) if you have any questions. This might change though, depending on the time needed to answer. Although, please note that this is free and open source software and there are no guarantees on any kind of support from our side.

# How to report a bug or request a feature

When submitting a bug report please use clear details about the issue you're facing, any additional dependencies you have, include the stacktrace if possible and any other details that would help us reproduce the issue.

If you're asking for a new feature, try to describe your use case and how to do think this feature would benefit yourself and/or others. Given that this repo is a free open source project, chances of your idea coming into fruition are much higher if you are also willing to contribute a PR. Please first open the issue, though, so we can discuss the feature before you have to spend time on it.

# How to create a PR

## License

Any contributions you make will be under the MIT Software License. In short, when you submit code changes, your submissions are understood to be under the same [MIT License](./LICENCE.md) that covers the project. Feel free to contact the maintainers if that's a concern.

## Rules

We strongly recommend to first open an issue discussing the contribution before creating a PR, unless you are really sure that the contribution does not need discussion (e.g. fixing a typo in documentation).

Please note that we can only merge a PR if:

* The code is following the official kotlin codestyle as defined by Jetbrains.
* All tests pass, and the code has 100% test coverage (run `./gradlew clean test` to run the checks). If it does not make sense to cover a certain line of code, please mention that in the PR.
* Bigger changes and new features are also covered by integration test(s).
* All relevant documentation is updated. Usually this means updating the KDoc of the code you work on, [README](README.md) and documentation in [doc](doc) dir.
* Additional dependencies are only added with a good reason.
* Code was reviewed by one of our regular contributors, taking into consideration code readability, security and whether the addition aligns with the long-term roadmap.

## Set up instructions

First please [fork this repository](https://docs.github.com/en/github/getting-started-with-github/fork-a-repo) to be able to contribute any changes.

The code in this codebase is managed by [Git](https://git-scm.com/) for version control and it uses [Gradle](https://gradle.org/) as a build tool. We use [gradle wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) so you normally don't need to install gradle separately.

You can run `./gradlew clean test` to download the dependencies and ensure that everything is set up correctly.

Now you can create a new branch describing the change you are about to make, e.g. `fix_typo_in_documentation`, and start coding.

## Your First Contribution

If you are interested in contributing, but don't have a specific issue at heart, we would recommend looking through the issues labelled "help wanted".

If you are new to contributing to open source, we recommend having a look at a [free tutorial](http://makeapullrequest.com/) for this. Issues labelled "good first issue" are meant specifically to get started in the repository.

If you are stuck at any point, feel free to comment in the issue you chose. We try to be as helpful to newcomers as possible, and you don't have to be afraid of dumb questions.
