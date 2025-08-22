---
title: pokemon
faker: games
---

## `Faker().pokemon`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/pokemon.yml:pokemon_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().pokemon.names() // => Bulbasaur

    Faker().pokemon.locations() // => Accumula Town

    Faker().pokemon.moves() // => Absorb
    ```
