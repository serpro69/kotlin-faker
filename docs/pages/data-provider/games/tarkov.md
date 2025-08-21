---
title: tea
---

## `Faker().tea`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/tarkov.yml:tarkov_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().tarkov.locations() // => Customs

    Faker().tarkov.traders() // => Prapor

    Faker().tarkov.weapons() // => SIG MCX

    Faker().tarkov.items() // => 42 Signature Blend English Tea

    Faker().tarkov.factions() // => USEC

    Faker().tarkov.bosses() // => Big Pipe

    Faker().tarkov.quests.prapor() // => Debut
    Faker().tarkov.quests.therapist() // => Shortage
    Faker().tarkov.quests.skier() // => Supplier
    Faker().tarkov.quests.peacekeeper() // => Fishing Gear
    Faker().tarkov.quests.mechanic() // => Gunsmith - Part 1
    Faker().tarkov.quests.ragman() // => Only Business
    Faker().tarkov.quests.jaeger() // => Acquaintance
    Faker().tarkov.quests.fence() // => Collector
    ```
