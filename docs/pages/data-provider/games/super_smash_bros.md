---
title: superSmashBros
faker: games
---

## `Faker().superSmashBros`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/super_smash_bros.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().superSmashBros.fighter() // => Bayonetta

    Faker().superSmashBros.stage() // => 3D Land
    ```
