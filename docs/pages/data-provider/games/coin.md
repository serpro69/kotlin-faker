---
title: coin
faker: games
faker: games
---

## `Faker().coin`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/coin.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().coin.flip() // => Heads || Tails
    ```
