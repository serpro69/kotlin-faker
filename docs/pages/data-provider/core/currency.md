---
title: currency
---

## `Faker().currency`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/currency.yml:currency_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().currency.code() // => AED

    Faker().currency.name() // => UAE Dirham

    Faker().currency.symbol() // => HK$
    ```
