---
title: phoneNumber
faker: core
---

## `Faker().phoneNumber`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/phone_number.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().phoneNumber.areaCode() // => 201
    Faker().phoneNumber.countryCode() // => 1
    Faker().phoneNumber.exchangecode() // => 321
    Faker().phoneNumber.phoneNumber() // => ###-###-#### where '#' is a random digit

    Faker().phoneNumber.cellPhone.number() // => ###-###-#### where '#' is a random digit
    ```
