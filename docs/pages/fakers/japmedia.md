## `JapaneseMediaFaker`

[![Central Sonatype](https://img.shields.io/maven-central/v/io.github.serpro69/kotlin-faker-japmedia?style=for-the-badge&logo=apachemaven&label=release-version&color=blue)](https://central.sonatype.com/artifact/io.github.serpro69/kotlin-faker-japmedia)
[![Central Sonatype (Snapshots)](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fcentral.sonatype.com%2Frepository%2Fmaven-snapshots%2Fio%2Fgithub%2Fserpro69%2Fkotlin-faker-japmedia%2Fmaven-metadata.xml&strategy=highestVersion&style=for-the-badge&logo=apachemaven&label=snapshot-version&color=yellow)](https://central.sonatype.com/service/rest/repository/browse/maven-snapshots/io/github/serpro69/kotlin-faker/)

Provides access to fake data generators within the Japanese Media domain.

## Usage

Documentation for kotlin-faker is available at [serpro69.github.io/kotlin-faker/](https://serpro69.github.io/kotlin-faker/).

### Downloading

Latest releases are always available on maven central.

**With gradle**

```groovy
dependencies {
    implementation 'io.github.serpro69:kotlin-faker:$version'
    implementation 'io.github.serpro69:kotlin-faker-japmedia:$version'
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
    <dependency>
        <groupId>io.github.serpro69</groupId>
        <artifactId>kotlin-faker-japmedia</artifactId>
        <version>${version}</version>
    </dependency>
</dependencies>
```  

_NB! An additional fake data provider like 'japmedia' requires the main `kotlin-faker` dependency to be on the classpath._

### Generating data

```kotlin
// NB! the package you import if using multiple fakers
import io.github.serpro69.kfaker.japmedia.faker

val faker = faker { }

faker.onePiece.characters()
faker.studioGhibli.characters()
```
