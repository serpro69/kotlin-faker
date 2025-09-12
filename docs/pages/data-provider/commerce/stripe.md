---
title: stripe
faker: commerce
---

## `Faker().stripe`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/stripe.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().stripe.validCards("visa") // => 4242424242424242

    Faker().stripe.validCards("") // => 4000056655665556

    Faker().stripe.validTokens("visa") // => tok_visa

    Faker().stripe.validTokens("") // => tok_mastercard

    Faker().stripe.invalidCards("addressZipFail") // => 4000000000000010

    Faker().stripe.invalidCards("") // => 4000000000000036
    ```
