---
title: emotion
faker: lorem
---

## `Faker().emotion`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/emotion.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().emotion.adjective() // => able

    Faker().emotion.noun() // => adoration
    ```
