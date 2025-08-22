---
title: heroesOfTheStorm
faker: games
---

## `Faker().heroesOfTheStorm`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/heroes_of_the_storm.yml:heroes_of_the_storm_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().heroesOfTheStorm.battlegrounds() // => Alterac Pass

    Faker().heroesOfTheStorm.classNames() // => Assasin

    Faker().heroesOfTheStorm.heroes() // => Abathur

    Faker().heroesOfTheStorm.quotes() // => Beat them like they owe you money!
    ```
