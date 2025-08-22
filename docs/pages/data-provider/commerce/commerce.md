---
title: commerce
faker: commerce
---

## `Faker().commerce`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/commerce.yml:commerce_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().commerce.department() // => Books

    Faker().commerce.productName() // => Intelligent Steel Coat

    Faker().commerce.promotionCode() // => Amazing Deal

    Faker().commerce.brand() // => Dell

    Faker().commerce.vendor() // => Amazon
    ```
