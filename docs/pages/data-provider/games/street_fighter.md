---
title: streetFighter
faker: games
---

## `Faker().streetFighter`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/street_fighter.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker.streetFighter.characters() // => Abel
    Faker.streetFighter.stages() // => A Shadow Falls stages
    Faker.streetFighter.quotes() // => Go home and be a family man.
    Faker.streetFighter.moves() // => A.X.E.
    ```
