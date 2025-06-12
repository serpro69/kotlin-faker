## `BooksFaker`

[![Central Sonatype](https://img.shields.io/maven-central/v/io.github.serpro69/kotlin-faker-books?style=for-the-badge)](https://central.sonatype.com/artifact/io.github.serpro69/kotlin-faker-books)
[![Central Sonatype (Snapshots)](https://img.shields.io/nexus/s/io.github.serpro69/kotlin-faker-books?label=snapshot-version&server=https%3A%2F%2Foss.sonatype.org&style=for-the-badge&color=yellow)](https://central.sonatype.com/service/rest/repository/browse/maven-snapshots/io/github/serpro69/kotlin-faker/)

Provides access to fake data generators within the Books domain.

## Usage

Documentation for kotlin-faker is available at [serpro69.github.io/kotlin-faker/](https://serpro69.github.io/kotlin-faker/).

### Downloading

Latest releases are always available on maven central.

**With gradle**

```groovy
dependencies {
    implementation 'io.github.serpro69:kotlin-faker:$version'
    implementation 'io.github.serpro69:kotlin-faker-books:$version'
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
        <artifactId>kotlin-faker-books</artifactId>
        <version>${version}</version>
    </dependency>
</dependencies>
```  

_NB! An additional fake data provider like 'books' requires the main `kotlin-faker` dependency to be on the classpath._

### Generating data

```kotlin
// NB! the package you import if using multiple fakers
import io.github.serpro69.kfaker.books.faker

val faker = faker { }

faker.book.title()
faker.cosmere.aons()
```
