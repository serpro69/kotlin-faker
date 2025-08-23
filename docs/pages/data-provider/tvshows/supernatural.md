---
title: supernatural
faker: tvshows
---

## `Faker().supernatural`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/supernatural.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().supernatural.character() // => Johnny

    Faker().supernatural.creature() // => Johnny's Place

    Faker().supernatural.weapon() // => "Oh hi, Denny"
    ```
