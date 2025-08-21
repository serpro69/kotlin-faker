---
title: blood
---

## `Faker().blood`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/blood.yml:blood_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().blood.type() // => O
    Faker().blood.rhFactor() // => +
    Faker().blood.group() // => O+
    ```
