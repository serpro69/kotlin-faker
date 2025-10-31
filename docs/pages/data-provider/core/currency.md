---
title: currency
faker: core
---

## `Faker().currency`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/currency.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().currency.code() // => AED

    Faker().currency.name() // => UAE Dirham

    Faker().currency.symbol() // => HK$
    ```
