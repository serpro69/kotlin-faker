---
title: electricalComponents
faker: tech
---

## `Faker().electricalComponents`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/electrical_components.yml:electrical_components_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().electricalComponents.active() // => Diode

    Faker().electricalComponents.passive() // => Resistor

    Faker().electricalComponents.electromechanical() // => Piezoelectric device
    ```
