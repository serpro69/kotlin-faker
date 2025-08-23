---
title: military
faker: misc
---

## `Faker().military`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/military.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().military.armyRank() // => Private

    Faker().military.marinesRank() // => Private

    Faker().military.navyRank() // => Seaman Recruit

    Faker().military.coastGuardRank() // => Seaman Recruit

    Faker().military.airForceRank() // => Airman Basic

    Faker().military.spaceForceRank() // => Airman Basic

    Faker().military.dodPaygrade() // => E-1
    ```
