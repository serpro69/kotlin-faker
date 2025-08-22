---
title: zelda
faker: games
---

## `Faker().zelda`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/zelda.yml:zelda_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().zelda.games() // => A Link to the Past

    Faker().zelda.characters() // => Abe

    Faker().zelda.locations() // => Akkala Ancient Tech Lab

    Faker().zelda.items() // => Blue Ring
    ```
