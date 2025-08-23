---
title: warhammerFantasy
faker: games
---

## `Faker().warhammerFantasy`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/warhammer_fantasy.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().warhammerFantasy.heroes() // => Alarielle the Radiant

    Faker().warhammerFantasy.quotes() // => Walls can't dodge!

    Faker().warhammerFantasy.locations() // => Nordland

    Faker().warhammerFantasy.factions() // => The Empire

    Faker().warhammerFantasy.creatures() // => Chaos Beastman
    ```
