Thanks for your interest in contributing to this project!
This project is free open source and as such dependent on your contributions as well.
These guidelines should help you get started more quickly and should ensure a smooth contribution process for both those contributing and those reviewing contributions.
Please read them thoroughly before contributing with a Pull Request, and at least skim them before adding an issue.

## How to ask for help

At the moment, it is totally fine to [open an issue](https://github.com/serpro69/kotlin-faker/blob/master/core/src/main/kotlin/io/github/serpro69/kfaker/Faker.kt) if you have any questions.
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
In short, when you submit code changes, your submissions are understood to be under the same [LICENSE](https://github.com/serpro69/kotlin-faker/blob/master/LICENSE.md) that covers the project.
Feel free to contact the maintainers if that's a concern.

### Rules

We strongly recommend to first open an issue discussing the contribution before creating a PR, unless you are really sure that the contribution does not need discussion (e.g. fixing a typo in documentation).

Please note that we can only merge a PR if:

* The code is following the official kotlin codestyle as defined by Jetbrains.
* All tests pass, and the code has 100% test coverage (run `./gradlew clean test` to run the checks). If it does not make sense to cover a certain line of code, please mention that in the PR.
* Bigger changes and new features are also covered by integration test(s) which are placed in an `integration` sub-package in test sources.
* All relevant documentation is updated. Usually this means updating the KDoc of the code you work on, [README.md](https://github.com/serpro69/kotlin-faker/blob/master/README.md) and documentation in [docs](https://github.com/serpro69/kotlin-faker/tree/master/docs) dir.
* Additional third-party dependencies are only added with a good reason.
* Code was reviewed by one of the regular contributors, taking into consideration code readability, security and whether the addition aligns with the long-term roadmap.

### Set up instructions

First please [fork this repository](https://docs.github.com/en/github/getting-started-with-github/fork-a-repo) to be able to contribute any changes.

The code in this codebase is managed by https://git-scm.com/[Git] for version control, and it uses https://gradle.org/[Gradle] as a build tool.
We use [gradle wrapper](https://github.com/serpro69/kotlin-faker/blob/master/core/src/main/kotlin/io/github/serpro69/kfaker/Faker.kt), so you normally don't need to install gradle separately.

You can run `./gradlew clean test` to download the dependencies and ensure that everything is set up correctly.

Now you can create a new branch describing the change you are about to make, e.g. `fix_typo_in_documentation`, and start coding.

### Your First Contribution

If you are interested in contributing, but don't have a specific issue at heart, we would recommend looking through the issues labelled [help wanted](https://github.com/serpro69/kotlin-faker/blob/master/core/src/main/kotlin/io/github/serpro69/kfaker/Faker.kt).

If you are new to contributing to open source, we recommend having a look at a http://makeapullrequest.com/[free tutorial] for this.
Issues labelled [good first issue](https://github.com/serpro69/kotlin-faker/blob/master/core/src/main/kotlin/io/github/serpro69/kfaker/Faker.kt) are meant specifically to get started in the repository.

If you are stuck at any point, feel free to comment in the issue you chose.
We try to be as helpful to newcomers as possible, and you don't have to be afraid of "dumb" questions.

_If this is your first pull request - please add yourself to the "contributors section" in [mkdocs.yml](https://github.com/serpro69/kotlin-faker/blob/36babb5c416d0ca79c90802bae950936309c3f57/mkdocs.yml#L76) file so that you get proper credit in the project's [About page](https://serpro69.github.io/kotlin-faker/dev/pages/about/)._

## Prerequisites

* JDK 8 (temurin or any other distribution; mandatory)
* JDK 11 (temurin or any other distribution; mandatory due to plugin dependencies)
* JDK 17 (GraalVM CE distribution; optional; needed for building `cli-bot` image)

Some details for the above requirements are as follows:

* To build any module of kotlin-faker, jdk version 11 or higher has to be used to run gradle processes due to [`org.graalvm.buildtools.native`](https://github.com/serpro69/kotlin-faker/blob/master/LICENSE.md) plugin requirements that is used in the [`cli-bot`](https://github.com/serpro69/kotlin-faker/blob/master/LICENSE.md) build file.
** [`core`](https://github.com/serpro69/kotlin-faker/tree/master/core) faker, and all additional [`faker`](https://github.com/serpro69/kotlin-faker/tree/master/faker) implementations will look up jdk 8 via [gradle jvm toolchains](https://github.com/serpro69/kotlin-faker/tree/master/faker) to make sure the libraries are built with java 8 compatibility.
** GraalVM CE is needed to build the native image of the [`cli-bot`](https://github.com/serpro69/kotlin-faker/tree/master/cli-bot) application. But since it's a module in the project and therefore is part of the project's build configuration process, it introduces a hard dependency on jdk version 11 or higher
*** GraalVM CE jdk distribution can be omitted if one does not want to build the native image of the `cli-bot` application, but any other jdk with version >= 11 is still mandatory to build the rest of the project as mentioned above.
*** GraalVM CE jdk can be installed with e.g. sdkman, or can be downloaded and installed directly from the [graalvm-ce-builds releases](https://github.com/serpro69/kotlin-faker/tree/master/cli-bot)
** The [`docs`](https://github.com/serpro69/kotlin-faker/tree/master/docs) module contains documentation written in [mkdocs](https://mkdocs.org).

## Code Structure

This repository consists of the following modules:

* `[bom](https://github.com/serpro69/kotlin-faker/tree/master/bom)` - [Bill-of-Materials](https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#bill-of-materials-bom-poms) for simplified faker dependency management
* `[cli-bot](https://github.com/serpro69/kotlin-faker/tree/master/cli-bot)` - a CLI application that helps to quickly find required kotlin-faker functions right from the terminal
* `[core](https://github.com/serpro69/kotlin-faker/tree/master/core)` - contains the core functionality of kotlin-faker
* `[docs](https://github.com/serpro69/kotlin-faker/tree/master/docs)` - documentation for this project, published @ https://serpro69.github.io/kotlin-faker/
* `[faker](https://github.com/serpro69/kotlin-faker/tree/master/faker)` - submodules with faker implementations in various domains, like "books", "music", and so on.
* `[extension](https://github.com/serpro69/kotlin-faker/tree/master/extension)` - additional faker "extensions" for popular third-party testing-related libraries
* `[test](https://github.com/serpro69/kotlin-faker/tree/master/test)` - "helper functions" for `[faker](https://github.com/serpro69/kotlin-faker/tree/master/faker)`s' integration tests

## Adding new functionality

This section describes how to add new functionality to kotlin-faker.

### Basics

Most of the data generated by kotlin-faker comes from `.yml` "dictionary" files located in [locales](https://github.com/serpro69/kotlin-faker/tree/master/core/src/main/resources/locales) directory.

A **data provider class** - e.g. `Address` - represents a certain "category" of fake data - in this case data related to address information.

Kotlin-faker also generates data in various **locales**, `en` being the default one.
The [locales/en](https://github.com/serpro69/kotlin-faker/tree/master/core/src/main/resources/locales/en) directory contains `.yml` files for all existing data providers, usually one file per provider, while localized dictionaries usually contain all the data in a single file, e.g. [locales/uk.yml](https://github.com/serpro69/kotlin-faker/tree/master/core/src/main/resources/locales/uk.yml).

### Adding a new Data Provider

Adding a new data provider is usually a straightforward process, as most of the "heavy lifting" does not change from one provider to another.
It does involve changes in several places though, which are explained in this section.

#### Add a new `.yml` file to [locales/en](https://github.com/serpro69/kotlin-faker/tree/master/core/src/main/resources/locales/en)

Let's take the [`name.yml`](https://github.com/serpro69/kotlin-faker/tree/master/core/src/main/resources/locales/en/name.yml) file for the `Name` provider, as an example:

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

#### Create the new data provider class under [provider](https://github.com/serpro69/kotlin-faker/tree/master/core/src/main/kotlin/io/github/serpro69/kfaker/provider) directory.

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

* the entry point for all data generation is the [`Faker`](https://github.com/serpro69/kotlin-faker/blob/master/core/src/main/kotlin/io/github/serpro69/kfaker/Faker.kt) class, so a new property needs to be added there that calls the data provider class

#### Update cli-bot fakers

* [`Constants.kt`](https://github.com/serpro69/kotlin-faker/blob/master/cli-bot/src/main/kotlin/io/github/serpro69/kfaker/app/Constants.kt) is used to provide all faker implementations to the faker command line application. If a new faker implementation is added, this needs to be updated

#### Update native-image [`reflect-config.json`](https://github.com/serpro69/kotlin-faker/blob/master/cli-bot/src/main/resources/META-INF/native-image/io.github.serpro69/cli-bot/reflect-config.json)

* this is used when building the native-image of the `faker-bot` CLI application and thus needs to be updated, otherwise the `nativeCompile` gradle task will fail
* it is easy to auto-update the configuration by using `native-image-agent` (requires the `native-image` binary to be installed):
** first create the jar of the app with `./gradlew clean shadowJar`
** then run each of the cli commands (include the verbose mode since that requires additional calls):
*** `java -agentlib:native-image-agent=config-merge-dir=temp_resources -jar cli-bot/build/libs/cli-bot-1.11.1-SNAPSHOT-fat.jar list --verbose`
*** `java -agentlib:native-image-agent=config-merge-dir=temp_resources -jar cli-bot/build/libs/cli-bot-1.11.1-SNAPSHOT-fat.jar lookup name --verbose`
** then copy the generated `reflect-config.json` from `temp_resources` dir (other json files usually don't need to be updated and can be ignored)

#### Next step is to update the documentation

* create a new `.md` file under [data-provider](https://github.com/serpro69/kotlin-faker/tree/master/docs/pages/data-provider) docs directory
** copy an existing file from that directory and replace the H2 header to reflect the correct faker provider property name, update the code snippet name (should be in the form of `<yml_filename>_provider_dict`) in the `[source,yaml]` section, and update the "available functions" code section as well

* if adding a new [faker](https://github.com/serpro69/kotlin-faker/tree/master/faker) module, make sure to update the [fakers](https://github.com/serpro69/kotlin-faker/tree/master/docs/pages/fakers) docs with a new page as well

#### Add tests

* new tests (usually) don't need to be added since integration tests are dynamically calling all public data provider functions via reflection
* a few changes need to be made to existing tests though if the new category name was added:
** the [`IntrospectorTest`](https://github.com/serpro69/kotlin-faker/blob/master/cli-bot/src/test/kotlin/io/github/serpro69/kfaker/app/cli/IntrospectorTest.kt) needs to be updated in the `cli-bot` module
** the [`TestConstants`](https://github.com/serpro69/kotlin-faker/blob/master/core/src/test/kotlin/io/github/serpro69/kfaker/TestConstants.kt) need to be updated as well

#### Examples

* In addition to the above instructions, you can also take a look at [`0b34d1`](https://github.com/serpro69/kotlin-faker/blob/master/core/src/main/kotlin/io/github/serpro69/kfaker/Faker.kt) commit, which can be used as an MVP example of all of the above steps.

* Also see [#222](https://github.com/serpro69/kotlin-faker/blob/master/core/src/main/kotlin/io/github/serpro69/kfaker/Faker.kt) for an example of adding a completely new faker implementation module

#### Which Faker implementation to use?

Since kotlin-faker 2.0, the functionality has been split between various "faker implementations", according to some generic "domains".

[CoreFaker](https://github.com/serpro69/kotlin-faker/tree/master/core) (or just Faker) contains the most commonly used data providers, like names, addresses, internet and others, while the rest has been split between various other [faker submodules](https://github.com/serpro69/kotlin-faker/tree/master/faker).

There's no "even split" between each faker implementation and in some cases you could easily argue for a data provider to belong to more than one domain, so use your best judgement when deciding where to place a new data provider implementation. If there's no good fit, use [MiscFaker](https://github.com/serpro69/kotlin-faker/tree/master/faker/misc).

One can also consider creating a new faker submodule altogether, however, a general rule should be that any faker implementation should have at least 3 data providers.
