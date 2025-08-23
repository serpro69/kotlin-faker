---
title: conan
faker: japmedia
---

## `Faker().conan`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/conan.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().conan.characters() // => Conan Edogawa

    Faker().conan.gadgets() // => Voice-Changing Bowtie

    Faker().conan.vehicles() // => Agasa's Volkswagen Beetle
    ```
