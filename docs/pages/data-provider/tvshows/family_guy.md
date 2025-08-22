---
title: familyGuy
faker: tvshows
---

## `Faker().familyGuy`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/family_guy.yml:family_guy_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().familyGuy.character() // => Peter Griffin
    Faker().familyGuy.location() // => Cleveland's Deli
    Faker().familyGuy.quote() // => It’s Peanut Butter Jelly Time.
    ```
