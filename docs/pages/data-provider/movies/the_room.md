---
title: theRoom
---

## `Faker().theRoom`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/the_room.yml:the_room_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().theRoom.actors() // => Tommy Wiseau

    Faker().theRoom.characters() // => Johnny

    Faker().theRoom.locations() // => Johnny's Place

    Faker().theRoom.quotes() // => "Oh hi, Denny"
    ```
