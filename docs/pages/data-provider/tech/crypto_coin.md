---
title: cryptoCoin
---

## `Faker().cryptoCoin`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/crypto_coin.yml:crypto_coin_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().cryptoCoin.coin() // => Bitcoin, BTC, https://i.imgur.com/psBNOBq.png
    ```
