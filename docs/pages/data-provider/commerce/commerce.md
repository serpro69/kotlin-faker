---
title: commerce
faker: commerce
---

## `Faker().commerce`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/commerce.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().commerce.department() // => Books

    Faker().commerce.productName() // => Intelligent Steel Coat

    Faker().commerce.promotionCode() // => Amazing Deal

    Faker().commerce.brand() // => Dell

    Faker().commerce.vendor() // => Amazon
    ```
