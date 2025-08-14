Thanks for your interest in contributing to this project!
This project is free open source and as such dependent on your contributions.
These guidelines should help you get started more quickly and should ensure a smooth contribution process for both those contributing and those reviewing contributions.
Please read them thoroughly before contributing with a Pull Request, and at least skim them before adding an issue.

## How to ask for help

At the moment, it is totally fine to https://github.com/serpro69/kotlin-faker/issues/new[open an issue] if you have any questions.
This might change though, depending on the time needed to answer.
Although, please note that this is free and open source software and there are no guarantees on any kind of support from our side.

## How to report a bug or request a feature

When submitting a bug report please use clear details about the issue you're facing, any additional dependencies you have, include the stacktrace if possible and any other details that would help us reproduce the issue.

If you're asking for a new feature, try to describe your use case and how to do think this feature would benefit yourself and/or others.
Given that this repo is a free open source project, chances of your idea coming into fruition are much higher if you are also willing to contribute a PR.
Please first open the issue, though, so we can discuss the feature before you have to spend time on it.

## How to create a PR

### License

Any contributions you make will be under the MIT Software License.
In short, when you submit code changes, your submissions are understood to be under the same link:LICENSE.adoc[LICENSE] that covers the project.
Feel free to contact the maintainers if that's a concern.

### Rules

We strongly recommend to first open an issue discussing the contribution before creating a PR, unless you are really sure that the contribution does not need discussion (e.g. fixing a typo in documentation).

Please note that we can only merge a PR if:

* The code is following the official kotlin codestyle as defined by Jetbrains.
* All tests pass, and the code has 100% test coverage (run `./gradlew clean test` to run the checks). If it does not make sense to cover a certain line of code, please mention that in the PR.
* Bigger changes and new features are also covered by integration test(s) which can be run with gradle `integrationTest` task.
* All relevant documentation is updated. Usually this means updating the KDoc of the code you work on, [README.md](https://github.com/serpro69/kotlin-faker/blob/master/README.md) and documentation in [docs](https://github.com/serpro69/kotlin-faker/tree/master/docs) dir.
* Additional third-party dependencies are only added with a good reason.
* Code was reviewed by one of the regular contributors, taking into consideration code readability, security and whether the addition aligns with the long-term roadmap.

### Set up instructions

First please [fork this repository](https://docs.github.com/en/github/getting-started-with-github/fork-a-repo) to be able to contribute any changes.

The code in this codebase is managed by https://git-scm.com/[Git] for version control, and it uses https://gradle.org/[Gradle] as a build tool.
We use https://docs.gradle.org/current/userguide/gradle_wrapper.html[gradle wrapper], so you normally don't need to install gradle separately.

You can run `./gradlew clean test` to download the dependencies and ensure that everything is set up correctly.

Now you can create a new branch describing the change you are about to make, e.g. `fix_typo_in_documentation`, and start coding.

### Your First Contribution

If you are interested in contributing, but don't have a specific issue at heart, we would recommend looking through the issues labelled https://github.com/serpro69/kotlin-faker/issues?q=is%3Aissue+is%3Aopen+sort%3Aupdated-desc+label%3A%22help+wanted+%3Asos%3A%22[help wanted].

If you are new to contributing to open source, we recommend having a look at a http://makeapullrequest.com/[free tutorial] for this.
Issues labelled https://github.com/serpro69/kotlin-faker/issues?q=is%3Aopen+label%3A%22good+first+issue+%3Ahammer%3A%22+sort%3Aupdated-desc[good first issue] are meant specifically to get started in the repository.

If you are stuck at any point, feel free to comment in the issue you chose.
We try to be as helpful to newcomers as possible, and you don't have to be afraid of "dumb" questions.

_If this is your first pull request - please add yourself to the "contributors section" in https://github.com/serpro69/kotlin-faker/blob/master/docs/src/orchid/resources/data.yml[data.yml] file so that you get proper credit in the project's https://serpro69.github.io/kotlin-faker/about/[About page]._

## Prerequisites

* JDK 8 (temurin or any other distribution; mandatory)
* JDK 11 (temurin or any other distribution; mandatory due to plugin dependencies)
* JDK 17 (GraalVM CE distribution; optional; needed for building `cli-bot` image)

Some details for the above requirements are as follows:

* To build any module of kotlin-faker, jdk version 11 or higher has to be used to run gradle processes due to link:https://graalvm.github.io/native-build-tools/latest/gradle-plugin.html[`org.graalvm.buildtools.native`] plugin requirements that is used in the link:cli-bot/build.gradle.kts[`cli-bot`] build file.
** link:core[`core`] faker, and all additional link:faker[`faker`] implementations will look up jdk 8 via link:https://docs.gradle.org/current/userguide/toolchains.html[gradle jvm toolchains] to make sure the libraries are built with java 8 compatibility.
** GraalVM CE is needed to build the native image of the link:cli-bot[`cli-bot`] application. But since it's a module in the project and therefore is part of the project's build configuration process, it introduces a hard dependency on jdk version 11 or higher
*** GraalVM CE jdk distribution can be omitted if one does not want to build the native image of the `cli-bot` application, but any other jdk with version >= 11 is still mandatory to build the rest of the project as mentioned above.
*** GraalVM CE jdk can be installed with e.g. sdkman, or can be downloaded and installed directly from the link:https://github.com/graalvm/graalvm-ce-builds/releases[graalvm-ce-builds releases]
** The link:docs[`docs`] module is currently built outside of the main kotlin-faker gradle proccesses. It depends on kotlin 1.4.31 and for this reason, the `docs` module has been removed from the kotlin-faker gradle build processes and should now be treated as a standalone project, with all gradle commands needing to be executed inside the `docs` directory. One can use either java 8 or 11 to build the documentation.

## Code Structure

This repository consists of the following modules:

* `link:cli-bot[cli-bot]` - a CLI application that helps to quickly find required kotlin-faker functions right from the terminal
* `link:core[core]` - contains the core functionality of kotlin-faker
* `link:docs[docs]` - documentation for this project, published @ https://serpro69.github.io/kotlin-faker/
* `link:faker[faker]` - submodules with faker implementations in various domains, like "books", "music", and so on.

## Adding new functionality

This section describes how to add new functionality to kotlin-faker.

### Basics

Most of the data generated by kotlin-faker comes from `.yml` "dictionary" files located in link:core/src/main/resources/locales[locales] directory.

A **data provider class** - e.g. `Address` - represents a certain "category" of fake data - in this case data related to address information.

Kotlin-faker also generates data in various **locales**, `en` being the default one.
The link:core/src/main/resources/locales/en[locales/en] directory contains `.yml` files for all existing data providers, usually one file per provider, while localized dictionaries usually contain all the data in a single file, e.g. link:core/src/main/resources/locales/uk.yml[locales/uk.yml].

### Adding a new Data Provider

Adding a new data provider is usually a straightforward process, as most of the "heavy lifting" does not change from one provider to another.
It does involve changes in several places though, which are explained in this section.

#### Add a new `.yml` file to link:core/src/main/resources/locales/en[locales/en]

Let's take the link:core/src/main/resources/locales/en/name.yml[`name.yml`] file for the `Name` provider, as an example:

[source,yml]
----
# core/src/main/resources/locales/en/name.yml
en:
  faker:
    name:
      male_first_name: [Aaron, Abdul, Abe]
      female_first_name: [Abbey, Abbie, Abby]
----

.Where:
* `en` is the locale name (in the case of a new data provider it's always going to be `en` as it's the default one)
* `name` is the data category name
* `male_first_name` and `female_first_name` are functions inside the `Name` class which generates random data.

For more details and examples take a look at some of existing `.yml` files.

#### Create the new data provider class under link:core/src/main/kotlin/io/github/serpro69/kfaker/provider[provider] directory.

The class needs to implement `YamlFakeDataProvider`, and override a few properties. Expanding on the same data provider example, let's look at the `Name` class implementation:

[source,kotlin]
----
class Name internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Name>(fakerService) {
    override val yamlCategory = YamlCategory.NAME
    override val localUniqueDataProvider = LocalUniqueDataProvider<Name>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun maleFirstName() = resolve("male_first_name")
    fun femaleFirstName() = resolve("female_first_name")
}
----

* the `category` property that uses the `YamlCategory.NAME` enum class, which has to be the same as declared in the `.yml` file. If the enum category does not already exist (Some dictionary files use the same category, which is perfectly fine to do if it makes sense) - new one should be added as well.
* the `YamlFakeDataProvider` provides a `resolve` function that should be used to get the random value for a given category key, i.e. `female_first_name`.

#### Add the property to `Faker` class

* the entry point for all data generation is the link:core/src/main/kotlin/io/github/serpro69/kfaker/Faker.kt[`Faker`] class, so a new property needs to be added there that calls the data provider class

#### Update cli-bot fakers

* link:cli-bot/src/main/kotlin/io/github/serpro69/kfaker/app/Constants.kt[`Constants.kt`] is used to provide all faker implementations to the faker command line application. If a new faker implementation is added, this needs to be updated

#### Update native-image link:cli-bot/src/main/resources/META-INF/native-image/io.github.serpro69/cli-bot/reflect-config.json[`reflect-config.json`]

* this is used when building the native-image of the `faker-bot` CLI application and thus needs to be updated, otherwise the `nativeCompile` gradle task will fail
* it is easy to auto-update the configuration by using `native-image-agent` (requires the `native-image` binary to be installed):
** first create the jar of the app with `./gradlew clean shadowJar`
** then run each of the cli commands (include the verbose mode since that requires additional calls):
*** `java -agentlib:native-image-agent=config-merge-dir=temp_resources -jar cli-bot/build/libs/cli-bot-1.11.1-SNAPSHOT-fat.jar list --verbose`
*** `java -agentlib:native-image-agent=config-merge-dir=temp_resources -jar cli-bot/build/libs/cli-bot-1.11.1-SNAPSHOT-fat.jar lookup name --verbose`
** then copy the generated `reflect-config.json` from `temp_resources` dir (other json files usually don't need to be updated and can be ignored)

#### Next step is to update the documentation

* create a new `.adoc` file under link:docs/src/orchid/resources/pages/data-provider[data-provider] directory
** copy an existing file from that directory and replace the H2 header to reflect the correct faker provider property name, update the code snippet name (should be in the form of `<yml_filename>_provider_dict`) in the `[source,yaml]` section, and update the "available functions" code section as well

* if adding a new link:faker[faker] module, update the docs link:docs/src/orchid/resources/config.yml[`config.yml`] file to include the API documentation
** make sure to update the link:docs/src/orchid/resources/pages/fakers[fakers] with a new page as well

#### Add tests

* new tests (usually) don't need to be added since integration tests are dynamically calling all public data provider functions via reflection
* a few changes need to be made to existing tests though if the new category name was added:
** the link:cli-bot/src/test/kotlin/io/github/serpro69/kfaker/app/cli/IntrospectorTest.kt[`IntrospectorTest`] needs to be updated in the `cli-bot` module
** the link:core/src/test/kotlin/io/github/serpro69/kfaker/TestConstants.kt[`TestConstants`] need to be updated as well

#### Examples

* In addition to the above instructions, you can also take a look at https://github.com/serpro69/kotlin-faker/commit/0b34d19d77aa728ed87382444908c90a63cc5f52[`0b34d1`] commit, which can be used as an MVP example of all of the above steps.

* Also see https://github.com/serpro69/kotlin-faker/pull/222[#222] for an example of adding a completely new faker implementation module

#### Which Faker implementation to use?

Since kotlin-faker 2.0, the functionality has been split between various "faker implementations", according to some generic "domains".

link:core[CoreFaker] (or just Faker) contains the most commonly used data providers, like names, addresses, internet and others, while the rest has been split between various other link:faker[faker submodules].

There's no "even split" between each faker implementation and in some cases you could easily argue for a data provider to belong to more than one domain, so use your best judgement when deciding where to place a new data provider implementation. If there's no good fit, use link:faker/misc[MiscFaker].

One can also consider creating a new faker submodule altogether, however, a general rule should be that any faker implementation should have at least 3 data providers.
