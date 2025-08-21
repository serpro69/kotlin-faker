---
title: separator
---

## `Faker().separator`

Provides functionality for getting locale-based separator symbol.

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
    Faker().separator.separator() // => &
    ```
