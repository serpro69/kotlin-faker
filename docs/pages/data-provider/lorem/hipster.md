---
title: hipster
faker: lorem
---

## `Faker().hipster`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/hipster.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().hipster.words() // => Wes Anderson
    ```
