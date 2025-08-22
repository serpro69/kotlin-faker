---
title: archer
faker: tvshows
---

## `Faker().archer`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/archer.yml:archer_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().archer.characters() // => Sterling Archer

    Faker().archer.locations() // => ISIS Headquarters

    Faker().archer.quotes() // => Lana. Lana. Lana!... Danger zone.
    ```
