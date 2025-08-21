---
title: dnd
---

## `Faker().dnd`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/dnd.yml:dnd_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().dnd.alignments() // => Chaotic Evil
    Faker().dnd.backgrounds() // => Acolyte
    Faker().dnd.cities() // => Almraiven
    Faker().dnd.klasses() // => Artificer
    Faker().dnd.languages() // => Abyssal
    Faker().dnd.meleeWeapons() // => Battleaxe
    Faker().dnd.monsters() // => Aarakocra
    Faker().dnd.races() // => Aarakocra
    Faker().dnd.rangedWeapons() // => Blowgun
    ```
