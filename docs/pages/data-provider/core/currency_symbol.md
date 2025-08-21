---
title: currencySymbol
---

## `Faker().currencySymbol`

Provides functionality for getting locale-based currency symbol.

.Dictionary file
[%collapsible]
====
[source,yaml]
----
{{ load ('../../../../core/src/main/resources/locales/en.yml') | raw }}
----
====

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().currencySymbol.symbol() // => $
    ```
