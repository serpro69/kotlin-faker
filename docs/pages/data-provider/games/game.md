---
title: game
faker: games
---

## `Faker().game`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/game.yml:game_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().game.title() // => Half-Life
    ```
