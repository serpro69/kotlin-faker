---
title: mountain
faker: travel
---

## `Faker().mountain`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/mountain.yml:mountain_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().mountain.name() // => Abi Gamin

    Faker().mountain.range() // => Annapurna Himalaya
    ```
