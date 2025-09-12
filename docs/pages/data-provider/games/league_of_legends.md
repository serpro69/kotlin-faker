---
title: leagueOfLegends
faker: games
---

## `Faker().leagueOfLegends`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/league_of_legends.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().leagueOfLegends.champion() // => Aatrox

    Faker().leagueOfLegends.location() // => Demacia

    Faker().leagueOfLegends.quote() // => Purge the unjust.

    Faker().leagueOfLegends.summonerSpell() // => Teleport

    Faker().leagueOfLegends.masteries() // => Battle Trance

    Faker().leagueOfLegends.rank() // => Bronze V
    ```
