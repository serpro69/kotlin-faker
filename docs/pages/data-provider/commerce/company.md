---
title: company
---

## `Faker().company`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/company.yml:company_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().company.buzzwords() // Advanced

    Faker().company.bs() // clicks-and-mortar

    Faker().company.name() // Smith and Sons

    Faker().company.industry() // Defense & Space

    Faker().company.profession // teacher

    Faker().company.type() // Public Company

    Faker().company.sicCode() // 0112
    ```
