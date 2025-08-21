---
title: princessBride
---

## `Faker().princessBride`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/princess_bride.yml:princess_bride_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().princessBride.characters() // Buttercup

    Faker().princessBride.quotes() // Hello. My name is Inigo Montoya. You killed my father. Prepare to die!
    ```
