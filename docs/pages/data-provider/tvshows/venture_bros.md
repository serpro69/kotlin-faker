---
title: ventureBros
faker: tvshows
---

## `Faker().ventureBros`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/venture_bros.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().ventureBros.character() // =>  Hank Venture

    Faker().ventureBros.organization() // =>  Team Venture

    Faker().ventureBros.vehicle() // =>  Adrian

    Faker().ventureBros.quote() // =>  Monarchs..don't sting..
    ```
