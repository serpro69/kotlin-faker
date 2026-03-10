---
title: mountain
faker: travel
---

## `Faker().mountain`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/mountain.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().mountain.name() // => Abi Gamin

    Faker().mountain.range() // => Annapurna Himalaya
    ```
