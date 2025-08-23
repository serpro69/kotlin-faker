---
title: business
faker: commerce
---

## `Faker().business`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/business.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().business.creditCardNumbers() // => 1234-2121-1221-1211

    Faker().business.creditCardTypes() // => visa
    ```
