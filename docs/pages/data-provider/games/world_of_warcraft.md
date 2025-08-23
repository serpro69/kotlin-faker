---
title: worldOfWarcraft
faker: games
---

## `Faker().worldOfWarcraft`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/world_of_warcraft.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().worldOfWarcraft.hero() // => Gul'dan

    Faker().worldOfWarcraft.quotes() // => An'u belore delen'na.

    Faker().worldOfWarcraft.classNames() // => Death Knight

    Faker().worldOfWarcraft.races() // => Draenei
    ```
