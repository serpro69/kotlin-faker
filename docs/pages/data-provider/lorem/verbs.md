---
title: verbs
faker: lorem
---

## `Faker().verbs`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/verbs.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().verbs.base() // => abash

    Faker().verbs.past() // => abashed

    Faker().verbs.pastParticiple() // => abashed

    Faker().verbs.simplePresent() // => abashes

    Faker().verbs.ingForm() // => abashing
    ```
