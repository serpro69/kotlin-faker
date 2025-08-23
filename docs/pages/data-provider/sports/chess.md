---
title: chess
faker: sports
---

## `Faker().chess`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/chess.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().chess.players() // => Alexander Alekhine

    Faker().chess.tournaments() // => Wijk aan Zee

    Faker().chess.openings() // => Alekhine’s Defense

    Faker().chess.titles() // => GM
    ```
