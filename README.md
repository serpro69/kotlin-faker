[![Stand With Ukraine](https://raw.githubusercontent.com/vshymanskyy/StandWithUkraine/main/banner-direct-single.svg)](https://stand-with-ukraine.pp.ua)

### <a href="https://github.com/serpro69/kotlin-faker"> <img src=./logo/name.png alt="kotlin-faker"/> </a>

> **Generates realistically-looking fake data**  
> Just like this fake-logo, but not quite so.  
> <img src=./logo/kotlin_faker.png height="144" alt="fake-logo"/>

[![Build Status](https://img.shields.io/github/actions/workflow/status/serpro69/kotlin-faker/build.yml?branch=master&logo=github&style=for-the-badge)](https://github.com/serpro69/kotlin-faker/actions/workflows/build.yml)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.serpro69/kotlin-faker?style=for-the-badge)](https://search.maven.org/artifact/io.github.serpro69/kotlin-faker)
[![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/io.github.serpro69/kotlin-faker?label=snapshot-version&server=https%3A%2F%2Foss.sonatype.org&style=for-the-badge&color=yellow)](#downloading)
[![Issues](https://img.shields.io/github/issues/serpro69/kotlin-faker.svg?logo=GitHub&style=for-the-badge&color=lightblue)](https://github.com/serpro69/kotlin-faker/issues)
![GitHub Top Lang](https://img.shields.io/github/languages/top/serpro69/kotlin-faker.svg?logo=Kotlin&logoColor=white&color=A97BFF&style=for-the-badge)
[![Awesome Kotlin](https://img.shields.io/badge/awesome-kotlin-orange?logo=Awesome-Lists&style=for-the-badge)](https://kotlin.link/resources?q=kotlin-faker)
[![Licence](https://img.shields.io/github/license/serpro69/kotlin-faker.svg?style=for-the-badge)](LICENSE.adoc)

## About

Port of a popular ruby [faker](https://github.com/stympy/faker) gem written in kotlin. Generates realistically looking fake data such as names, addresses, banking details, and many more, that can be used for testing and data-anonymization purposes.

## Prerequisites

- Faker libraries depend on java 8 and kotlin 1.9.x for runtime
- Building faker from source requires both jdk 8 (temurin is recommended, but any vendor should work) and jdk 17 (graalvm-ce). The latter is needed to build the [cli-bot](cli-bot) application, but since it's a module in the project and therefore is part of the project's build process, the dependency on graalvm-ce jdk is only partially optional:
  - To build faker, jdk version 11 or higher has to be used with gradle due to [`org.graalvm.buildtools.native`](https://graalvm.github.io/native-build-tools/latest/gradle-plugin.html) plugin requirements that is used in the [`cli-bot`](cli-bot/build.gradle.kts) build file. 
  - GraalVM CE jdk distribution can be omitted if one does not want to build the native image of the `cli-bot` application, but any other jdk with version >= 11 is still mandatory to build the rest of the project as mentioned above.
  - GraalVM CE jdk can be installed with e.g. sdkman, or can be downloaded and installed directly from the [graalvm-ce-builds releases](https://github.com/graalvm/graalvm-ce-builds/releases)

## Usage

Documentation for kotlin-faker is available at [serpro69.github.io/kotlin-faker/](https://serpro69.github.io/kotlin-faker/).

> [!WARNING]
> **kotlin-faker 2.0** is coming, which, apart from new features, also means breaking changes.
> 
> Presently this will affect users of snapshot versions, which are built from 'master'.
> 
> More details on currently added breaking changes and migration can be found in [#220](https://github.com/serpro69/kotlin-faker/issues/220)

### Downloading

Latest releases are always available on maven central.

**With gradle**

```groovy
dependencies {
    implementation 'io.github.serpro69:kotlin-faker:$version'
}
```  

**With maven**

```xml
<dependencies>
    <dependency>
        <groupId>io.github.serpro69</groupId>
        <artifactId>kotlin-faker</artifactId>
        <version>${version}</version>
    </dependency>
</dependencies>
```  

***Snapshots are also available using the following repository: `https://oss.sonatype.org/content/repositories/snapshots/`***

**With gradle**
```groovy
repositories {
    maven {
        url = 'https://oss.sonatype.org/content/repositories/snapshots/'
    }
}
```

**With maven**
```xml
<repositories>
    <repository>
        <id>sonatype-snapshot</id>
        <name>Sonatype Snapshot</name>
        <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
    </repository>
</repositories>
```

#### Additional Fakers

Extra fakers covering a wide range of domains are available as separate dependencies. See [faker](faker) submodules in this repo for more details about each faker.

### Generating data

```kotlin
val faker = faker { }

faker.name.firstName() // => Ana
faker.address.city() // => New York
```

## CLI

Command line application can be used for a quick lookup of faker functions. See [faker-bot README](cli-bot/README.md)
for installation and usage details.

## Test

To run unit tests: `./gradlew clean test`

To run integration tests: `./gradlew clean integrationTest`

## Build and Deploy

To deploy to OSS Sonatype repo:

- set the following properties in `~/.gradle/gradle.properties`
    - `signing.keyId=<key_id>`
    - `signing.password=<key_passphrase>`
    - `signing.secretKeyRingFile=/home/user/.gnupg/secring.gpg`
    - `sonatypeUsername=<oss_user_token>`
    - `sonatypePassword=<oss_password_token>`
    - `stagingProfileId=<oss_staging_profile_id>`
- running `publishFakerCorePublicationToSonatypeRepository` will publish the artifacts to either staging release repo or to snapshots repo, depending on the current version

### Bumping versions

Versions need to be bumped manually through a tag with the next release version that has to follow the
[semver](https://semver.org/) rules, and the tag has to be pushed to origin.

Creating the tag can be either done manually with `git tag` or by using `gradlew tag` task.

#### Pre-releases

To create a new pre-release version (new release candidate)
the following can be used: `./gradlew clean tag -Prelease -PnewPreRelease -PbumpComponent={comp}`, where `comp` can be
one of the following values: `major`, `minor`, or `patch`.

To bump an existing pre-release to the next version (next release candidate for the same release version)
the following can be used: `./gradlew clean tag -Prelease -PpreRelease`.

#### Releases

To promote a pre-release to a release version the following can be used:
`./gradlew clean tag -Prelease -PpromoteToRelease`,

To create a new release version the following can be used:
`./gradlew clean tag -Prelease -PbumpComponent={comp}`, where `comp` can be one of the following values: `major`
, `minor`, or `patch`.

#### Make targets

Alternatively to the above targets from [Makefile](Makefile) can be used for the same purposes.

## Contributing

The [CONTRIBUTING](CONTRIBUTING.adoc) guidelines should help in getting you started on how to contribute to this project.

## Thanks

Many thanks to these awesome tools that help us in creating open-source software:  
[![Intellij IDEA](https://cloud.google.com/tools/images/icon_IntelliJIDEA.png)](http://www.jetbrains.com/idea)
[![YourKit Java profiler](https://www.yourkit.com/images/yklogo.png)](https://www.yourkit.com/features/)

## Licence

This code is free to use under the terms of the MIT licence. See [LICENSE](LICENSE.adoc).
