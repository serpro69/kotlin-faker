---
title: appliance
---

## `Faker().appliance`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/appliance.yml:appliance_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().appliance.brand() //  => Bosch

    Faker().appliance.quimpment() // => Air ioniser
    ```
