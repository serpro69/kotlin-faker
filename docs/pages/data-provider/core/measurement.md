---
title: measurement
faker: core
---

## `Faker().measurement`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/measurement.yml:measurement_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().measurement.height() // => inch

    Faker().measurement.length() // => yard

    Faker().measurement.volume() // => cup

    Faker().measurement.weight() // => pound

    Faker().measurement.metricHeight() // => centimeter

    Faker().measurement.metricLength() // => millimeter

    Faker().measurement.metricVolume() // => milliliter

    Faker().measurement.metricWeight() // => milligram
    ```
