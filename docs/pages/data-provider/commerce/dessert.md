---
title: dessert
faker: commerce
---

## `Faker().dessert`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/dessert.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().dessert.variety() // => Cake

    Faker().dessert.topping() // => Rainbow Sprinkles

    Faker().dessert.flavor() // => Vanilla

    Faker().dessert.dessert() // => Vanilla Cake with Rainbow Sprinkles
    ```
