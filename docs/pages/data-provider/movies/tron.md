---
title: tron
faker: movies
---

## `Faker().tron`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/tron.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().tron.characters(TronCharacterType.USERS) // => Alan Bradley

    Faker().tron.games() // => Arc Wars

    Faker().tron.locations() // => Deleted Program Storage and Processing

    Faker().tron.quotes(TronCharacter.ALAN_BRADLEY) // => I still don't understand why you want to break into the system.

    Faker().tron.taglines() // => In the future video games battles will be a matter of life and death.

    Faker().tron.vehicles() // => Battle Tank

    Faker().tron.alternateCharacterSpellings(TronAlternateCharacter.ALAN_BRADLEY) // => alan
    ```
