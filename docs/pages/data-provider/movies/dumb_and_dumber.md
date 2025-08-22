---
title: dumbAndDumber
faker: movies
---

## `Faker().dumbAndDumber`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/dumb_and_dumber.yml:dumb_and_dumber_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().dumbAndDumber.actors() // => Jim Carrey

    Faker().dumbAndDumber.characters() // => Lloyd Christmas

    Faker().dumbAndDumber.quotes() // => Just when I thought you couldn't possibly be any dumber, you go and do something like this... and totally redeem yourself!
    ```
