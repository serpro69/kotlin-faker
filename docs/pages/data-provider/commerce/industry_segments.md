---
title: industrySegments
faker: commerce
---

## `Faker().industrySegments`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/industry_segments.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().industrySegments.industry() // => Oil & Gas

    Faker().industrySegments.superSector() // => Oil & Gas

    Faker().industrySegments.sector() // => Oil & Gas Producers

    Faker().industrySegments.subSector() // => Exploration & Production
    ```
