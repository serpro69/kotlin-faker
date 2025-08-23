---
title: appliance
faker: tech
---

## `Faker().appliance`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/appliance.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().appliance.brand() //  => Bosch

    Faker().appliance.quimpment() // => Air ioniser
    ```
