---
title: fallout
---

## `Faker().fallout`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/fallout.yml:fallout_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().fallout.characters() // => Ada

    Faker().fallout.factions() // => Boomers

    Faker().fallout.locations() // => Anchorage

    Faker().fallout.quotes() // => Ad Victoriam!
    ```
