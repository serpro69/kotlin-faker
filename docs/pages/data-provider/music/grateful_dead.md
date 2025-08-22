---
title: gratefulDead
faker: music
---

## `Faker().gratefulDead`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/grateful_dead.yml:grateful_dead_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().gratefulDead.players() // => Jerry Garcia

    Faker().gratefulDead.songs() // => Touch of Grey
    ```
