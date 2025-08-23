---
title: heyArnold
faker: tvshows
---

## `Faker().heyArnold`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/hey_arnold.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().heyArnold.characters() // => Arnold

    Faker().heyArnold.locations() // => P.S. 118

    Faker().heyArnold.quotes() // => Stoop Kid's afraid to leave his stoop!
    ```
