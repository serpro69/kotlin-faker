---
title: nation
---

## `Faker().nation`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/nation.yml:nation_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().nation.nationality() // => Afghans

    Faker().nation.language() // => Nepali

    Faker().nation.capital_city() // => Kabul
    ```

'''

.Non-implemented Functions:
[%collapsible]
====
[source,kotlin]
----
Faker().nation.flag() // => [240, 159, 135, 166, 240, 159, 135, 168]
----
====
