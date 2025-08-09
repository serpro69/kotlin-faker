---
icon: simple/rocket
---

# :beginner: Getting Started

## Installing

### Releases

Installation is as simple as adding `kotlin-faker` dependency to your build configuration file:

=== "gradle"

    ```kotlin
    testImplementation("io.github.serpro69:kotlin-faker:$fakerVersion")
    ```

=== "maven"

    ```xml
    <dependency>
        <groupId>io.github.serpro69</groupId>
        <artifactId>kotlin-faker</artifactId>
        <version>${kotlin-faker.version}</version>
    </dependency>
    ```

Release artifacts are available for download from maven central, and you usually don't need to add any additional repositories information.

### Snapshots

Snapshot are automatically published on each commit to master. If you want to try out the latest functionality - add the dependency the same way as described above, but change the version to the current snapshot version, and add the sonatype snapshots repository to your repositories block in the build configuration file:

=== "gradle"

    ```kotlin
    repositories {
        maven {
            url = URI("https://central.sonatype.com/repository/maven-snapshots/")
        }
    }
    ```

=== "maven"

    ```xml
    <repositories>
        <repository>
            <id>sonatype-snapshot</id>
            <name>Sonatype Snapshot</name>
            <url>https://central.sonatype.com/repository/maven-snapshots/</url>
        </repository>
    </repositories>
    ```

### Faker BOM

See [Kotlin-faker BOM](./faker-bom.md) page for details on how to use a Bill-of-Materials to simplify dependency management.

## Generating Data

Creating a `Faker` instance can be done either by creating a class instance directly:

=== "kotlin"

    ```kotlin
    val faker = Faker()

    faker.name.firstName()
    faker.address.city()
    ```

=== "java"

    ```java
    Faker faker = new Faker();

    faker.getName().firstName()
    faker.getAddress().city()
    ```

Or by using the [Faker DSL](./faker-dsl.md) (Which also gives you dsl-like access to [Faker Configuration](./faker-configuration.md).)

=== "kotlin"

    ```kotlin
    val faker = faker {
        // faker config
    }

    faker.name.firstName()
    faker.address.city()
    ```


=== "functional-java"

    ```java
    Faker faker = faker(fromConsumer(f -> {
        // faker config
    }));

    faker.getName().firstName()
    faker.getAddress().city()
    ```

=== "java"

    ```java
    Faker faker = faker(f -> {
        // faker config
        return Unit.INSTANCE;
    });

    faker.getName().firstName()
    faker.getAddress().city()
    ```

!!! tip
    For Java users (1) 
    { .annotate }

    1. Note the usage of `FunctionalUtil.fromConsumer` method in "functional-java" tab. 
    If this is not used, then an explicit return must be specified at the end of the lambda (See "java" tab instead).
    <br>
    See also [Java Interop](./java-interop.md) for more details on using kotlin-faker from Java.

This concludes this short "getting started" guide. Jump to [Faker Configuration](./faker-configuration.md) page to learn how to configure `Faker` to generate localized data, ensure deterministic random data generation and other configuration options or just click the next button to go to next wiki page.
