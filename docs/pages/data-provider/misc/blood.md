---
title: blood
faker: misc
---

## `Faker().blood`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/blood.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().blood.type() // => O
    Faker().blood.rhFactor() // => +
    Faker().blood.group() // => O+
    ```
