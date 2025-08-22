---
title: invoice
faker: commerce
---

## `Faker().invoice`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/invoice.yml:invoice_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    ```

---

??? info "non-implemented functions"
    === "kotlin :material-language-kotlin:"
        ```kotlin
        Faker().invoice.checkDigitMethod() // method_731

        Faker().invoice.pattern() // '\d{3,19}#'
        ```
