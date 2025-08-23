---
title: superhero
faker: games
---

## `Faker().superhero`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/superhero.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().superhero.power() // =>  Ability Shift

    Faker().superhero.name() // => Alien Man
    ```
