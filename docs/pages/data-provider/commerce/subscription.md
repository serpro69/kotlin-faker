---
title: subscription
faker: commerce
---

## `Faker().subscription`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/subscription.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().subscription.plans() // => Free Trial

    Faker().subscription.statuses() // => Active

    Faker().subscription.paymentMethods() // => Credit card

    Faker().subscription.subscriptionTerms() // => Daily

    Faker().subscription.paymentTerms() // => Payment in advance
    ```
