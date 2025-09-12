---
title: pokemon
faker: games
---

## `Faker().pokemon`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/pokemon.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().pokemon.names() // => Bulbasaur

    Faker().pokemon.locations() // => Accumula Town

    Faker().pokemon.moves() // => Absorb
    ```
