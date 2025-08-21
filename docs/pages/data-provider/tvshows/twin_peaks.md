---
title: twinPeaks
---

## `Faker().twinPeaks`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/twin_peaks.yml:twin_peaks_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().twinPeaks.characters() // => Albert Rosenfield

    Faker().twinPeaks.locations() // => Big Ed's Gas Farm

    Faker().twinPeaks.quotes() // => She's dead... Wrapped in plastic.
    ```
