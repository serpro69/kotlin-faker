---
title: adjective
faker: lorem
---

## `Faker().adjective`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/adjective.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().adjective.positive() // => adorable

    Faker().adjective.negative() // => aggressive
    ```
