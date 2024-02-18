## `TravelFaker`

[![Maven Central](https://img.shields.io/maven-central/v/io.github.serpro69/kotlin-faker-travel?style=for-the-badge)](https://search.maven.org/artifact/io.github.serpro69/kotlin-faker-travel)
[![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/io.github.serpro69/kotlin-faker-travel?label=snapshot-version&server=https%3A%2F%2Foss.sonatype.org&style=for-the-badge&color=yellow)](#downloading)

Provides access to fake data generators within the Travel domain.

## Usage

Documentation for kotlin-faker is available at [serpro69.github.io/kotlin-faker/](https://serpro69.github.io/kotlin-faker/).

### Downloading

Latest releases are always available on maven central.

**With gradle**

```groovy
dependencies {
    implementation 'io.github.serpro69:kotlin-faker:$version'
    implementation 'io.github.serpro69:kotlin-faker-travel:$version'
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
        <artifactId>kotlin-faker-travel</artifactId>
        <version>${version}</version>
    </dependency>
</dependencies>
```  

_NB! An additional fake data provider like 'travel' requires the main `kotlin-faker` dependency to be on the classpath._

### Generating data

```kotlin
// NB! the package you import if using multiple fakers
import io.github.serpro69.kfaker.travel.faker

val faker = faker { }

faker.airport.europeanUnion.iataCode()
faker.mountain.name()
```
