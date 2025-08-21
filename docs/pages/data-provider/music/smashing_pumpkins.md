---
title: smashingPumpking
---

## `Faker().smashingPumpking`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/smashing_pumpkins.yml:smashing_pumpkins_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().smashingPumpkins.musicians() // => Billy Corgan

    Faker().smashingPumpkins.albums() // => Gish

    Faker().smashingPumpkins.lyric() // => I am one as you are three, try to find a messiah in your trinity. Your city to burn, your city to burn, try to look for something in your city to burn, you'll burn

    Faker().smashingPumpkins.songs() // => "...Said Sadly"
    ```
