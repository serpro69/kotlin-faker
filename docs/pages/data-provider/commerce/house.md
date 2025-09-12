---
title: house
faker: commerce
---

## `Faker().house`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/house.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().house.furniture() // => chair

    Faker().house.rooms() // => kitchen
    ```
