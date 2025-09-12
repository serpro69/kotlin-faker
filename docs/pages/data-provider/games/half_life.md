---
title: halfLife
faker: games
---

## `Faker().halfLife`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/half_life.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().halfLife.character() // => Adrian Shephard

    Faker().halfLife.enemy() // => APC

    Faker().halfLife.location() // => Black Mesa East
    ```
