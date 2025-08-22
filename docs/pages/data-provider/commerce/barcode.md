---
title: barcode
faker: commerce
---

## `Faker().barcode`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/barcode.yml:barcode_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    // `#` represents a random digit
    // `?` represents a random letter
    Faker().barcode.ean8() // => #######
    Faker().barcode.ean13() // => ############
    Faker().barcode.upcA() // => ###########
    Faker().barcode.upcE() // => 0######
    Faker().barcode.compositeSymbol() // => ????####
    Faker().barcode.isbn() // => 978#########
    Faker().barcode.ismn() // => 9790########
    Faker().barcode.issn() // => 977#########
    ```
