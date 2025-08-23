---
title: nation
faker: travel
---

## `Faker().nation`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/nation.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().nation.nationality() // => Afghans

    Faker().nation.language() // => Nepali

    Faker().nation.capital_city() // => Kabul
    ```

---

??? info "non-implemented functions"
    === "kotlin :material-language-kotlin:"
        ```kotlin
        Faker().nation.flag() // => [240, 159, 135, 166, 240, 159, 135, 168]
        ```
