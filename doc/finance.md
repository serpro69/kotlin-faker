# `Faker().finance`

[Dictionary file](../src/main/resources/locales/en/finance.yml)

Available Functions:  
```kotlin
```

Non-implemented Functions:
```kotlin
// Random credit card number by card type
Faker().finance.credit_card("visa") // => /4###########L/
// or by a random card type
Faker().finance.credit_card("") // => /6771-89##-####-###L/

// Random VAT number by country code
Faker().finance.vat_number("AT") // => "ATU########"
// or from a random country
Faker().finance.vat_number("") // => "ATU########"
```
