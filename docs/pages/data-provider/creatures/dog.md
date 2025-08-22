---
title: dog
faker: creatures
---

## `Faker().dog`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/dog.yml:dog_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().dog.name() // => Buddy

    Faker().dog.breed() // => Affenpinscher

    Faker().dog.sound() // => woof

    Faker().dog.memePhrase() // => heck no pal

    Faker().dog.age() // => puppy

    Faker().dog.coatLength() // => hairless

    Faker().dog.size() // => small
    ```
