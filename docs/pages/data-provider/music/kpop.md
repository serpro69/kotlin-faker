---
title: kPop
faker: music
---

## `Faker().kPop`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/kpop.yml:kpop_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().kPop.firstGroups() // => Seo Taiji and Boys

    Faker().kPop.secondGroups() // => CB Mass

    Faker().kPop.thirdGroups() // => APeace

    Faker().kPop.girlGroups() // => 2NB

    Faker().kPop.boyBands() // => 1TYM

    Faker().kPop.solo() // => Ailee
    ```
