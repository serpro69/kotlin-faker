---
title: simpsons
---

## `Faker().simpsons`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/simpsons.yml:simpsons_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().simpsons.characters() // => Homer Simpson

    Faker().simpsons.locations() // => Springfield

    Faker().simpsons.quotes() // => Marriage is like a coffin and each kid is another nail.

    Faker().simpsons.episodeTitles() // => Simpsons Roasting on an Open Fire
    ```
