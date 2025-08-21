---
title: emotion
---

## `Faker().emotion`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/emotion.yml:emotion_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().emotion.adjective() // => able

    Faker().emotion.noun() // => adoration
    ```
