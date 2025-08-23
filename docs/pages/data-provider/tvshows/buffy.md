---
title: buffy
faker: tvshows
---

## `Faker().buffy`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/buffy.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().buffy.characters() // => Buffy Summers

    Faker().buffy.quotes() // => In every generation there is a chosen one.

    Faker().buffy.actors() // => Sarah Michelle Geller

    Faker().buffy.big_bads() // => The Master

    Faker().buffy.episodes() // => Welcome to the Hellmouth
    ```
