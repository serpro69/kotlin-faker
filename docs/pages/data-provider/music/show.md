---
title: show
---

## `Faker().show`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/show.yml:show_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().show.adultMusical() // => Aladdin (Prince Street Players Version)

    Faker().show.play() // => A Delicate Balance

    Faker().show.kidsMusical() // => Disney's Alice in Wonderland JR.
    ```
