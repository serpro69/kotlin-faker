---
title: verbs
---

## `Faker().verbs`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/verbs.yml:verbs_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().verbs.base() // => abash

    Faker().verbs.past() // => abashed

    Faker().verbs.pastParticiple() // => abashed

    Faker().verbs.simplePresent() // => abashes

    Faker().verbs.ingForm() // => abashing
    ```
