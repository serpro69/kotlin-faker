---
title: halfLife
---

## `Faker().halfLife`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/half_life.yml:half_life_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().halfLife.character() // => Adrian Shephard

    Faker().halfLife.enemy() // => APC

    Faker().halfLife.location() // => Black Mesa East
    ```
