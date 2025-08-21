---
title: sport
---

## `Faker().sport`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/sport.yml:sport_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().sport.summerOlympics() // => 3x3 basketball

    Faker().sport.winterOlympics() // => Alpine skiing

    Faker().sport.summerParalympics() // => Archery

    Faker().sport.winterParalympics() // => Alpine skiing

    Faker().sport.ancientOlympics() // => Boxing

    Faker().sport.unusual() // => Apple Racing
    ```
