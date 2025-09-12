---
title: cosmere
faker: books
---

## `Faker().cosmere`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/cosmere.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().cosmere.aons() // => Aon

    Faker().cosmere.shardWorlds() // => Ashyn

    Faker().cosmere.shards() // => Devotion

    Faker().cosmere.surges() // => Abrasion

    Faker().cosmere.knightsRadiant() // => Bondsmith

    Faker().cosmere.metals() // => Steel

    Faker().cosmere.allomancers() // => Mistborn

    Faker().cosmere.feruchemists() // => Feruchemist

    Faker().cosmere.heralds() // => Jezrien

    Faker().cosmere.sprens() // => Alespren
    ```
