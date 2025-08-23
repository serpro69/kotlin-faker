---
title: cultureSeries
faker: books
---

## `Faker().cultureSeries`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/culture_series.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().cultureSeries.books() // => Consider Phlebas

    Faker().cultureSeries.cultureShips() // => Happy Idiot Talk

    Faker().cultureSeries.culture_ship_classes() // => General Systems Vehicle

    Faker().cultureSeries.culture_ship_class_abvs() // => GSV

    Faker().cultureSeries.civs() // => 'Ktik

    Faker().cultureSeries.planets() // => Bulthmaas
    ```
