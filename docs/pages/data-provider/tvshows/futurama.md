---
title: futurama
---

## `Faker().futurama`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/futurama.yml:futurama_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().futurama.characters() // => Abner Doubledeal
    Faker().futurama.locations() // => Akbar
    Faker().futurama.quotes() // => A fancy dress gala? I'll wear my formal shell.
    Faker().futurama.hermesCatchphrases() // => Cursed bacteria of Liberia!
    ```
