---
title: departed
faker: movies
---

## `Faker().departed`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/departed.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().departed.actors() // => Leonardo DiCaprio

    Faker().departed.characters() // => Billy Costigan

    Faker().departed.quotes() // => I'm the guy who does his job. You must be the other guy.
    ```
