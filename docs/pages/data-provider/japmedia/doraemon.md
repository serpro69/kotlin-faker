---
title: doraemon
faker: japmedia
---

## `Faker().doraemon`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/doraemon.yml:doraemon_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().doraemon.characters() // => Doraemon

    Faker().doraemon.gadgets() // => Doraemon Tool Catalog

    Faker().doraemon.locations() // => Africa
    ```
