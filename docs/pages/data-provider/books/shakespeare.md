---
title: shakespeare
---

## `Faker().shakespeare`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/shakespeare.yml:shakespeare_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().shakespeare.hamlet() // => To be, or not to be: that is the question.

    Faker().shakespeare.asYouLikeIt() // => All the world 's a stage, and all the men and women merely players. They have their exits and their entrances; And one man in his time plays many parts.

    Faker().shakespeare.kingRichardTheThird() // => Now is the winter of our discontent.

    Faker().shakespeare.romeoAndJuliet() // => O Romeo, Romeo! wherefore art thou Romeo?.
    ```
