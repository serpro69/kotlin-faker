---
title: cryptoCoin
faker: tech
---

## `Faker().cryptoCoin`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/crypto_coin.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().cryptoCoin.coin() // => Bitcoin, BTC, https://i.imgur.com/psBNOBq.png
    ```
