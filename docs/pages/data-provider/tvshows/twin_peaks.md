---
title: twinPeaks
faker: tvshows
---

## `Faker().twinPeaks`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/twin_peaks.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().twinPeaks.characters() // => Albert Rosenfield

    Faker().twinPeaks.locations() // => Big Ed's Gas Farm

    Faker().twinPeaks.quotes() // => She's dead... Wrapped in plastic.
    ```
