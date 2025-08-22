---
title: rush
faker: music
---

## `Faker().rush`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/rush.yml:rush_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().rush.players() // => Geddy Lee
    Faker().rush.albums() // => Rush
    ```
