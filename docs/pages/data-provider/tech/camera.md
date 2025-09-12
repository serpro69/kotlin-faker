---
title: camera
faker: tech
---

## `Faker().camera`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/camera.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().camera.brand() // => Canon

    Faker().camera.model() // => 450D

    Faker().camera.brandWithModel() // => Benq G2F
    ```
