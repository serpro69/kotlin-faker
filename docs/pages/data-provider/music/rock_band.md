---
title: rockBand
faker: music
---

## `Faker().rockBand`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/rock_band.yml:rock_band_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().rockBand.name() // => Led Zeppelin
    Faker().rockBand.song() // => Are You Gonna Be My Girl
    ```
