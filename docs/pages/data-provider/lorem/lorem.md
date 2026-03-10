---
title: lorem
faker: lorem
---

## `Faker().lorem`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/lorem.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().lorem.words() // => alias

    Faker().lorem.supplemental() // => abbas

    Faker().lorem.punctuation() // => '.'
    ```

---

??? info "non-implemented functions"
    === "kotlin :material-language-kotlin:"
        ```kotlin
        Faker().lorem.multibyte() // => [240, 159, 152, 128]
        ```
