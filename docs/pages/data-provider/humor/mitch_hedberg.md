---
title: mitchHedberg
faker: humor
---

## `Faker().mitchHedberg`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/mitch_hedberg.yml:mitch_hedberg_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().mitchHedberg.quote() // => An escalator can never break, it can only become stairs. You should never see an 'Escalator Temporarily Out Of Order' sign, just 'Escalator Temporarily Stairs. Sorry for the convenience'.
    ```
