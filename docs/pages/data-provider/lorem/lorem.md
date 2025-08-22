---
title: lorem
faker: lorem
---

## `Faker().lorem`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/lorem.yml:lorem_provider_dict"
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
