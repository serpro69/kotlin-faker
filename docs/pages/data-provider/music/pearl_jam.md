---
title: pearlJam
faker: music
---

## `Faker().pearlJam`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/pearl_jam.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().pearlJam.musicians() // => Boom Gaspar

    Faker().pearlJam.albums() // => Backspacer

    Faker().pearlJam.songs() // => Alive
    ```
