---
title: name
faker: core
---

## `Faker().name`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/name.yml:name_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().name.maleFirstName() // => Aaron

    Faker().name.femaleFirstName() // => Abbey

    Faker().name.neutralFirstName() // => Alexis

    // Male of Female first name
    Faker().name.firstName() // => Aaron || Abbey

    Faker().name.lastName() // => Abbott

    Faker().name.name() // => Mr. John Smith

    Faker().name.nameWithMiddle() // => Dr. John Abrams Smith

    Faker().name.maleLastName() // => Adams

    Faker().name.femaleLastName() // => Adams
    ```
