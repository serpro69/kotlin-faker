---
title: chiquito
faker: humor
---

## `Faker().chiquito`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/chiquito.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().chiquito.expressions() // => ¡Aguaaa, aguaaa!
    Faker().chiquito.terms() // => ¡Cobarde!
    Faker().chiquito.sentences() // => ¡Siete caballos vienen de Bonanza!
    Faker().chiquito.jokes() // => - Papár papár llévame al circo!\n
                                   - Noorl! El que quiera verte que venga a la casa
    ```
