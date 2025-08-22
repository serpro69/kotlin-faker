---
title: idNumber
faker: core
---

## `Faker().idNumber`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/id_number.yml:id_number_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().idNumber.invalid() // => 000-##-#### where '#' is a random digit
    ```

---

??? info "non-implemented functions"
    === "kotlin :material-language-kotlin:"
        ```kotlin
        Faker().idNumber.valid() // => #{IDNumber.ssn_valid}
        ```
