---
title: separator
faker: core
---

## `Faker().separator`

Provides functionality for getting locale-based separator symbol.

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().separator.separator() // => &
    ```
