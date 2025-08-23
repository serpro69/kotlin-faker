---
title: bank
faker: commerce
---

## `Faker().bank`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/bank.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().bank.name() // => UBS CLEARING AND EXECUTION SERVICES LIMITED

    Faker().bank.swiftBic() // => AACCGB21

    // IBAN Code (fetched by country code, case-insensitive)
    Faker().bank.ibanDetails("ua") // => 1234567890123456789012345
    // or a random one
    Faker().bank.ibanDetails("") // => 12345678901234
    ```
