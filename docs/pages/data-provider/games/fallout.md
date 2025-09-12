---
title: fallout
faker: games
---

## `Faker().fallout`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/fallout.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().fallout.characters() // => Ada

    Faker().fallout.factions() // => Boomers

    Faker().fallout.locations() // => Anchorage

    Faker().fallout.quotes() // => Ad Victoriam!
    ```
