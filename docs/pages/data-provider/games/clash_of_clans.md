---
title: clashOfClans
faker: games
---

## `Faker().clashOfClans`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/clash_of_clan.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().clashOfClans.troops() // => Barbarian

    Faker().clashOfClans.ranks() // => Unranked

    Faker().clashOfClans.defensiveBuildings() // => Cannon
    ```
