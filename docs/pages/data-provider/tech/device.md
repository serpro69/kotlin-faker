---
title: device
faker: tech
---

## `Faker().device`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/device.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().device.modelName() // => iPhone

    Faker().device.platform() // => Android OS

    Faker().device.manufacturer() // => Dell

    Faker().device.serial() // => pEekWH7zGxVITv6NTa5KHjLSwr5Ie4
    ```
