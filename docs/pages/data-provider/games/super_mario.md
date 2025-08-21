---
title: superMario
---

## `Faker().superMario`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/super_mario.yml:super_mario_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().superMario.characters() // => Mario

    Faker().superMario.games() // => Luigi's Mansion

    Faker().superMario.locations() // => Bonneton
    ```
