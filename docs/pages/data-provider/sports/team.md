---
title: team
faker: sports
---

## `Faker().team`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/team.yml:team_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().team.name() // => Los Angeles bees

    Faker().team.sport() // => baseball

    Faker().team.mascot() // => Raymond
    ```
