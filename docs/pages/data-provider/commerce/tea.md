---
title: tea
---

## `Faker().tea`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/tea.yml:tea_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().tea.variety.black() // => Assam
    Faker().tea.variety.oolong() // => Alishan
    Faker().tea.variety.green() // => Bancha
    Faker().tea.variety.white() // => Bai Mu Dan
    Faker().tea.variety.herbal() // => Anise

    Faker().tea.type() // => Black
    ```
