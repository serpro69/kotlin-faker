---
title: animal
faker: creatures
---

## `Faker().animal`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/animal.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    // Name
    Faker().animal.name() // => alpaca
    ```
