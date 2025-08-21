---
title: phish
---

## `Faker().phish`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/phish.yml:phish_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().phish.albums() // => Junta
    Faker().phish.musicians() // => Jon Fishman
    Faker().phish.songs() // => A Song I Heard the Ocean Sing
    ```
