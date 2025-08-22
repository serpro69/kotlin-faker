---
title: elderScrolls
faker: games
---

## `Faker().elderScrolls`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/elder_scrolls.yml:elder_scrolls_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().elderScrolls.race() // => Altmer

    Faker().elderScrolls.creature() // => Bear

    Faker().elderScrolls.region() // => Black Marsh

    Faker().elderScrolls.dragon() // => Dragon

    Faker().elderScrolls.city() // => Solitude

    Faker().elderScrolls.firstName() // => Balgruuf

    Faker().elderScrolls.lastName() // => The Old

    Faker().elderScrolls.weapon() // => Alessandra's Dagger

    Faker().elderScrolls.jewelry() // => Copper and Moonstone Circlet
    ```
