---
title: heroes
---

## `Faker().heroes`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/heroes.yml:heroes_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().heroes.names() // => Christian

    Faker().heroes.specialties() // => Ballista

    Faker().heroes.klasses() // => Knight

    Faker().heroes.artifacts() // => Admiral's Hat
    ```
