---
title: superMario
faker: games
---

## `Faker().superMario`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/super_mario.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().superMario.characters() // => Mario

    Faker().superMario.games() // => Luigi's Mansion

    Faker().superMario.locations() // => Bonneton
    ```
