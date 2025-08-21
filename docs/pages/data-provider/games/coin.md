---
title: coin
---

## `Faker().coin`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/coin.yml:coin_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().coin.flip() // => Heads || Tails
    ```
