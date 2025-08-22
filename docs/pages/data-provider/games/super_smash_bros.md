---
title: superSmashBros
faker: games
---

## `Faker().superSmashBros`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/super_smash_bros.yml:super_smash_bros_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().superSmashBros.fighter() // => Bayonetta

    Faker().superSmashBros.stage() // => 3D Land
    ```
