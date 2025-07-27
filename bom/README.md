## `kotlin-faker-bom`

[![Central Sonatype](https://img.shields.io/maven-central/v/io.github.serpro69/kotlin-faker-bom?style=for-the-badge&logo=apachemaven&label=release-version&color=blue)](https://central.sonatype.com/artifact/io.github.serpro69/kotlin-faker-bom)
[![Central Sonatype (Snapshots)](https://img.shields.io/nexus/s/io.github.serpro69/kotlin-faker-bom?label=snapshot-version&server=https%3A%2F%2Foss.sonatype.org&style=for-the-badge&color=yellow)](https://central.sonatype.com/service/rest/repository/browse/maven-snapshots/io/github/serpro69/kotlin-faker/)

Kotlin-faker provides a [Bill-of-Materials](https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#bill-of-materials-bom-poms) that simplifies dependency management.

## Usage

Documentation for kotlin-faker is available at [serpro69.github.io/kotlin-faker/](https://serpro69.github.io/kotlin-faker/).

### Downloading

Latest releases are always available on maven central.

**With gradle**

To [import Maven BOM with Gradle](https://docs.gradle.org/current/userguide/platforms.html#sub:bom_import), a platform dependency needs to be declared on the `kotlin-faker-bom`. The rest of `kotlin-faker` dependencies do not need to specify the versions explicitly as they will be pulled from the BOM

```groovy
dependencies {
    implementation platform('io.github.serpro69:kotlin-faker-bom:$version')
    implementation 'io.github.serpro69:kotlin-faker'
    implementation 'io.github.serpro69:kotlin-faker-books'
    implementation 'io.github.serpro69:kotlin-faker-tech'
    // rest of dependencies
}
```  

**With maven**

To use the BOM, a dependency on `kotlin-faker-bom` needs to be declared in the `<dependencyManagement>` block of the `pom.xml`, file:

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>io.github.serpro69</groupId>
            <artifactId>kotlin-faker-bom</artifactId>
            <version>${kotlin-faker.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

Then add the dependencies as usual but w/o specifying the versions, which will be pulled from the BOM:

```xml
<dependencies>
    <dependency>
        <groupId>io.github.serpro69</groupId>
        <artifactId>kotlin-faker</artifactId>
    </dependency>
    <dependency>
        <groupId>io.github.serpro69</groupId>
        <artifactId>kotlin-faker-books</artifactId>
    </dependency>
    <dependency>
        <groupId>io.github.serpro69</groupId>
        <artifactId>kotlin-faker-tech</artifactId>
    </dependency>
    <!-- rest of dependencies as needed -->
</dependencies>
```  
