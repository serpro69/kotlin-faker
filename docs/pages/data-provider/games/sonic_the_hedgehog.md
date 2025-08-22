---
title: sonicTheHedgehog
faker: games
---

## `Faker().sonicTheHedgehog`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/sonic_the_hedgehog.yml:sonic_the_hedgehog_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().sonicTheHedgehog.zone() // => Aerobase Zone

    Faker().sonicTheHedgehog.character() // => Alf-Layla-wa-Layla

    Faker().sonicTheHedgehog.game() // => Sonic the Hedgehog
    ```
