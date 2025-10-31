---
title: minecraft
faker: games
---

## `Faker().minecraft`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/minecraft.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().minecraft.achievement() // => Acquire Hardware
    Faker().minecraft.biome() // => Badlands
    Faker().minecraft.blocks() // => Stone
    Faker().minecraft.enchantment() // => Aqua Affinity
    Faker().minecraft.gameMode() // => Adventure
    Faker().minecraft.items() // => Iron Shovel
    Faker().minecraft.mobs() // => Sheep
    Faker().minecraft.statusEffect() // => Absorption
    ```
