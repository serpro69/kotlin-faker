---
title: house
---

## `Faker().house`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/house.yml:house_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().house.furniture() // => chair

    Faker().house.rooms() // => kitchen
    ```
