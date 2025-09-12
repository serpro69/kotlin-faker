---
title: theater
faker: music
---

## `Faker().theater`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/theater.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().theater.adultMusical() // => Aladdin (Prince Street Players Version)

    Faker().theater.play() // => A Delicate Balance

    Faker().theater.kidsMusical() // => Disney's Alice in Wonderland JR.
    ```
