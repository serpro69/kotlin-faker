---
title: rickAndMorty
faker: tvshows
---

## `Faker().rickAndMorty`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/rick_and_morty.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().rickAndMorty.characters() // => Rick Sanchez

    Faker().rickAndMorty.locations() // => Dimension C-132

    Faker().rickAndMorty.quotes() // => Ohh yea, you gotta get schwifty.
    ```
