---
title: archer
faker: tvshows
---

## `Faker().archer`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/archer.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().archer.characters() // => Sterling Archer

    Faker().archer.locations() // => ISIS Headquarters

    Faker().archer.quotes() // => Lana. Lana. Lana!... Danger zone.
    ```
