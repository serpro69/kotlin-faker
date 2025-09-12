---
title: lovecraft
faker: books
---

## `Faker().lovecraft`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/lovecraft.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().lovecraft.fhtagn() // => Ph'nglui mglw'nafh Cthulhu R'lyeh wgah'nagl fhtagn

    Faker().lovecraft.deity() // => Azathoth

    Faker().lovecraft.location() // => Arkham

    Faker().lovecraft.tome() // => Necronomicon

    Faker().lovecraft.words() // => abnormal
    ```
