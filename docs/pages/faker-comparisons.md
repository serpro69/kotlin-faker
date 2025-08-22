---
title: Faker Comparisons
---

# JVM-targeted Faker Libs Comparison

On the surface **kotlin-faker** isn't "unique" by any means and there exist other JVM-targeted libraries out there with similar functionalities, so you have several options to choose from.

So why use this one instead? I've decided to make a comparison between **kotlin-faker**, and other JVM-based libs that have been out there for quite some time.

!!! info
    The benchmarks time is an average execution time of 10 consecutive runs on the system with the following specs:

    ```text
    OS: Ubuntu 20.04.4 LTS x86_64
    Host: Precision 5530
    Kernel: 5.13.0-41-generic
    CPU: Intel i9-8950HK (12) @ 4.800GHz
    GPU: NVIDIA Quadro P2000 Mobile
    GPU: Intel UHD Graphics 630
    Memory: 21277MiB / 31728MiB
    ```

    Each run includes creating a new Faker instance and generating a 1_000_000 values with the function returning a person's full name:

    === "kotlin :material-language-kotlin:"
        ```kotlin
        fun main() {
            List(10) { it }.sumOf {
                measureTimeMillis {
                    val f = Faker()
                    repeat(1_000_000) { f.name.name() }
                }
            } / 10
        }
        ```

    !!! note
        Benchmarks for `blocoio/faker` could not be done due to unexpected exceptions coming from the lib, benchmarks for `moove-it/fakeit` could not be done due to android dependencies in the lib._

|                     | [kotlin-faker](https://github.com/serpro69/kotlin-faker) | [datafaker-net/datafaker](https://github.com/datafaker-net/datafaker) | [DiUS/java-faker](https://github.com/DiUS/java-faker) | [Devskiller/jfairy](https://github.com/Devskiller/jfairy) | [blocoico/faker](https://github.com/blocoio/faker) | [moove-it/fakeit](https://github.com/moove-it/fakeit) |
| ------------------- | --- | --- | --- | --- | --- | --- |
| Language            | :material-language-kotlin: | :material-language-java: | :material-language-java: | :material-language-java: | :material-language-java: | :material-language-kotlin: |
| [No. of Providers](../wiki/data-providers.md)<br>(`address`, `name`, etc.) | 213 | [docs](https://www.datafaker.net/documentation/providers/) | 73 | 8 | 21 | 36 |
| [No. of Locales](../wiki/available-locales.md)<br>(`nb-NO`, `uk`, `es` etc.) | 60 | [69](https://github.com/datafaker-net/datafaker/blob/2ae4fd5c64e6df06247cf2f7846f7eb7792aa862/README.md?plain=1#L478-L547) | | 47 | 10 | 46 | 44 |
| [Unique Values](../wiki/unique-generator.md) | &#10004; | &#10004; | &#10007; | &#10007; | &#10007; | &#10007; |
| Extra Functionality | &#10004; | &#10004; | &#10007; | &#10004; | &#10007; | &#10007; |
| [Kotlin DSL](../wiki/faker-dsl.md) | &#10004; | &#10007; | &#10007; | &#10007; | &#10007; | &#10007; |
| [CLI App](../wiki/faker-cli.md) | &#10004; | &#10007; | &#10007; | &#10007; | &#10007; | &#10007; |
| Actively maintained | &#10004; | &#10004; | &#10007; | &#10007; | &#10007; | &#10007; |
| Benchmarks          | v1.11.0 - 2890ms | v1.4.0 - 2950ms | v1.0.2 - 12500ms | v0.6.5 - 10300ms | NA | NA |
|                     | kotlin-faker | datafaker-net/datafaker | DiUS/java-faker | Devskiller/jfairy | blocoico/faker | moove-it/fakeit |
