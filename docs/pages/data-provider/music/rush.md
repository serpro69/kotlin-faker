---
title: rush
faker: music
---

## `Faker().rush`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/rush.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().rush.players() // => Geddy Lee
    Faker().rush.albums() // => Rush
    ```
