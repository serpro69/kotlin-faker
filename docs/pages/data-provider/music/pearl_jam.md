---
title: pearlJam
---

## `Faker().pearlJam`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/pearl_jam.yml:pearl_jam_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().pearlJam.musicians() // => Boom Gaspar

    Faker().pearlJam.albums() // => Backspacer

    Faker().pearlJam.songs() // => Alive
    ```
