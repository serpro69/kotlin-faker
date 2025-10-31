---
title: beer
faker: commerce
---

## `Faker().beer`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/beer.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().beer.brand() // => Corona Extra

    Faker().beer.name() // => Pliny The Elder

    Faker().beer.hop() // => Amarillo

    Faker().beer.yeast() // => 1007 - German Ale

    Faker().beer.malt() // => Caramel

    Faker().beer.style() // => Light Lager
    ```
