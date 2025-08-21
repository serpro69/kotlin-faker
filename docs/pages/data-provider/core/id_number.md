---
title: idNumber
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

'''

.Non-implemented Functions:
[%collapsible]
====
[source,kotlin]
----
Faker().idNumber.valid() // => #{IDNumber.ssn_valid}
----
====
