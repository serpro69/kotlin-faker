---
title: theOffice
faker: tvshows
---

## `Faker().theOffice`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/the_office.yml:the_office_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().theOffice.characters() // => Michael Scott

    Faker().theOffice.quotes() // => "Everything I have I owe to this job… this stupid, wonderful, boring, amazing job.",
    ```
