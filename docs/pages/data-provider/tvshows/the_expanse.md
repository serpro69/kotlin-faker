---
title: theExpanse
faker: tvshows
---

## `Faker().theExpanse`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/the_expanse.yml:the_expanse_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().theExpanse.characters()// => Jim Holden

    Faker().theExpanse.locations()// => Earth

    Faker().theExpanse.ships()// => Rocinante

    Faker().theExpanse.quotes()// => Give the Martians their water! Milowda na animals. You have every right to be angry. You should be angry. But if we act like animals, we only justify their belief that we are. Gif im fo imalowda xitim. Treat them the way they should treat us.
    ```
