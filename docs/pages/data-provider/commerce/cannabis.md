---
title: cannabis
---

## `Faker().cannabis`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/cannabis.yml:cannabis_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().cannabis.strains() // => 24k

    Faker().cannabis.cannabinoidAbbreviations() // => THC

    Faker().cannabis.cannabinoids() // => Tetrahydrocannabinol

    Faker().cannabis.terpenes() // => α Pinene

    Faker().cannabis.medicalUses() // => analgesic

    Faker().cannabis.healthBenefits() // => relieves pain

    Faker().cannabis.categories() // => capsules

    Faker().cannabis.types() // => hybrid

    Faker().cannabis.buzzwords() // => blunt wrap

    Faker().cannabis.brands() // => 8 | FOLD Cultivation
    ```
