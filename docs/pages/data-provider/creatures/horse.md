---
title: horse
faker: creatures
---

## `Faker().horse`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/horse.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().horse.name() // => Ebony
    Faker().horse.breed() // => Abaco Barb
    ```
