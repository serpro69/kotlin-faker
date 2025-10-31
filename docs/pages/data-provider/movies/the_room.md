---
title: theRoom
faker: movies
---

## `Faker().theRoom`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/the_room.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().theRoom.actors() // => Tommy Wiseau

    Faker().theRoom.characters() // => Johnny

    Faker().theRoom.locations() // => Johnny's Place

    Faker().theRoom.quotes() // => "Oh hi, Denny"
    ```
