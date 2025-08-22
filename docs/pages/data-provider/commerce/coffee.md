---
title: coffee
faker: commerce
---

## `Faker().coffee`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/coffee.yml:coffee_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().coffee.country() // => Brazil

    // => Region by country (case-insensitive)
    Faker().coffee.regions("brazil") // => Sul Minas
    // => or random one
    Faker().coffee.regions("") // => Sidama

    Faker().coffee.variety() // => Liberica

    Faker().coffee.notes() // => mild silky mint sage dill

    Faker().coffee.blendName() // => Summer Solstice
    ```
