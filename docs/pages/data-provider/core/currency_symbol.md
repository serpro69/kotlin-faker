---
title: currencySymbol
faker: core
---

## `Faker().currencySymbol`

Provides functionality for getting locale-based currency symbol.

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().currencySymbol.symbol() // => $
    ```
