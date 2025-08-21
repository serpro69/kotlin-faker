---
title: dessert
---

## `Faker().dessert`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/dessert.yml:dessert_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().dessert.variety() // => Cake

    Faker().dessert.topping() // => Rainbow Sprinkles

    Faker().dessert.flavor() // => Vanilla

    Faker().dessert.dessert() // => Vanilla Cake with Rainbow Sprinkles
    ```
