---
title: science
faker: edu
---

## `Faker().science`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/science.yml:science_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    // Scientific Branches
    Faker().sciense.branch.empiricalNaturalBasic() // => Physics
    Faker().sciense.branch.empiricalNaturalApplied() // => Engineering
    Faker().sciense.branch.empiricalSocialBasic() // => Anthropology
    Faker().sciense.branch.empiricalSocialApplied() // => Business Administration
    Faker().sciense.branch.formalBasic() // => Logic
    Faker().sciense.branch.formalApplied() // => Computer Science

    Faker().science.element() // => Hydrogen

    Faker().science.elementSymbol() // => H

    Faker().science.elementState() // => Gas

    Faker().science.elementSubcategory() // => Alkali metal

    Faker().science.modifier() // => Quantum

    Faker().science.scientist() // => Isaac Newton

    Faker().science.tool() // => Microscope
    ```
