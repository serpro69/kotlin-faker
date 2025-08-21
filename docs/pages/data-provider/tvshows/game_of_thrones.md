---
title: gameOfThrones
---

## `Faker().gameOfThrones`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/game_of_thrones.yml:game_of_thrones_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().gameOfThrones.characters() // => Abelar Hightower

    Faker().gameOfThrones.houses() // => Flint of Flint's Finger

    Faker().gameOfThrones.cities() // => Braavos

    Faker().gameOfThrones.quotes() // => There are no heroes...in life, the monsters win.

    Faker().gameOfThrones.dragons() // => Drogon
    ```
