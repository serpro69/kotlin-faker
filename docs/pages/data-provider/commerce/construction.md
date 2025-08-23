---
title: construction
faker: commerce
---

## `Faker().construction`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/construction.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().construction.heavyEquipment() // => Excavator

    Faker().construction.materials() // => Aluminum

    Faker().construction.subcontractCategories() // => Asphalt Paving

    Faker().construction.roles() // => Construction Manager

    Faker().construction.trades() // => Boilermaker

    Faker().construction.standardCostCodes() // => 1 - General Requirements
    ```
