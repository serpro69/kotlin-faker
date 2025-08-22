---
title: finance
faker: commerce
---

## `Faker().finance`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/finance.yml:finance_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    // Random credit card number by card type
    Faker().finance.credit_card("visa") // => /4###########L/
    // or by a random card type
    Faker().finance.credit_card("") // => /6771-89##-####-###L/

    // Random condominium fiscal code
    Faker().finance.condominiumFiscalCode() // => ######### (only IT data for now)

    // Random VAT number by country code
    Faker().finance.vat_number("AT") // => "ATU########"
    // or from a random country
    Faker().finance.vat_number("") // => "ATU########"

    Faker().finance.ticker(NASDAQ) // => AMZN
    Faker().finance.ticker(NYSE) // => XOM
    Faker().finance.stockMarket() // => NYSE
    ```
