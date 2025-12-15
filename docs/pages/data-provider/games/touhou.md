---
title: touhou
faker: games
---

## `Faker().touhou`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/touhou.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().touhou.games() // => Highly Responsive to Prayers

    Faker().touhou.characters() // => Alice Margatroid

    Faker().touhou.spell_cards() // => Spirit Sign "Yin-Yang Sign"

    Faker().touhou.locations() // => Abandon Pond

    Faker().touhou.songs() // => Welcome to the Moon Tour
    ```
