---
title: camera
---

## `Faker().camera`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/camera.yml:camera_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().camera.brand() // => Canon

    Faker().camera.model() // => 450D

    Faker().camera.brandWithModel() // => Benq G2F
    ```
