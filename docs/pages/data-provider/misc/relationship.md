---
title: relationship
faker: misc
---

## `Faker().relationship`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/relationship.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().relationship.familialDirect() // => Father

    Faker().relationship.familialExtended() // => Grandfather

    Faker().relationship.familial()// => Grandmother

    Faker().relationship.inLaw()// => Father-in-law

    Faker().relationship.spouse()// => Husband

    Faker().relationship.parent()// => Father

    Faker().relationship.sibling()// => Sister
    ```
