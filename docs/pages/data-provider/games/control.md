---
title: control
---

## `Faker().control`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/control.yml:control_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().control.character() // => Ahti

    Faker().control.location() // => Access Corridor

    Faker().control.objectOfPower() // => Service Weapon

    Faker().control.alteredItem() // => "Get Well Soon" Balloon

    Faker().control.alteredWorldEvent() // => Albany, New York

    Faker().control.hiss() // => You are a worm through time.

    Faker().control.theBoard() // => < Another crisis/workday resolved >

    Faker().control.quote() // => 'Let's get cleaning' she said, cocking her gun
    ```
