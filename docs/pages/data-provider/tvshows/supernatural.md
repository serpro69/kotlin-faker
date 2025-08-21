---
title: supernatural
---

## `Faker().supernatural`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/supernatural.yml:supernatural_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().supernatural.character() // => Johnny

    Faker().supernatural.creature() // => Johnny's Place

    Faker().supernatural.weapon() // => "Oh hi, Denny"
    ```
