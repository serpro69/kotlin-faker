---
title: measurement
faker: core
---

## `Faker().measurement`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/measurement.yml"
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
