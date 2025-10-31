---
title: volleyball
faker: sports
---

## `Faker().volleyball`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/volleyball.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().volleyball.team() // => A.S.D. Pallavolo Torino

    Faker().volleyball.player() // => Aaron Russell

    Faker().volleyball.coach() // => Al Scates

    Faker().volleyball.position() // => Defensive Specialist

    Faker().volleyball.formation() // => 4-2
    ```
