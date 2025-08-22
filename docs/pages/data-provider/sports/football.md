---
title: football
faker: sports
---

## `Faker().football`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/football.yml:football_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().football.teams() // => Real Madrid

    Faker().football.players() // => Lionel Messi

    Faker().football.coaches() // => Ernesto Valverde

    Faker().football.competitions() // => UEFA European Championship

    Faker().football.positions() // => Goalkeeper
    ```
