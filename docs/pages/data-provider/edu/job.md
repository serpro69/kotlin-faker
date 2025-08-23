---
title: job
faker: edu
---

## `Faker().job`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/job.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().job.field() // => Marketing

    Faker().job.seniority() // => Lead

    Faker().job.position() // => Supervisor

    Faker().job.keySkills() // => Teamwork

    Faker().job.employmentType() // => Full-time

    Faker().job.educationLevel() // => Associates

    Faker().job.title() // => IT Manager
    ```
