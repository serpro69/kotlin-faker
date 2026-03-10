---
title: gender
faker: core
---

## `Faker().gender`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/gender.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().gender.types() // => Female
    Faker().gender.binaryTypes() // => Female
    Faker().gender.shortBinaryTypes() // => f
    ```
