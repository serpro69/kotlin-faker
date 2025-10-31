---
title: worldCup
faker: sports
---

## `Faker().worldCup`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/world_cup.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().worldCup.teams() // => Egypt

    Faker().worldCup.stadiums() // => Ekaterinburg Arena

    Faker().worldCup.cities() // => Saint Petersburg
    ```

---

??? info "non-implemented functions"
    === "kotlin :material-language-kotlin:"
        ```kotlin
        Faker().worldCup.groups() // => ["Australia", "Denmark", "France", "Peru"]

        Faker().worldCup.rosters("Peru").coach() // => ["Ricardo Gareca"]

        Faker().worldCup.rosters("Peru").goalkeepers() // => ["Pedro Gallese", "Jose Carvallo", "Carlos Caceda"]

        Faker().worldCup.rosters("Peru").defenders() // => ["Luis Abram", "Luis Advincula", "Miguel Araujo", "Aldo Corzo", "Nilson Loyola", "Christian Ramos", "Alberto Rodriguez", "Anderson Santamaría", "Miguel Trauco"]

        Faker().worldCup.rosters("Peru").midfielders() // => ["Pedro Aquino", "Wilmer Cartagena", "Christian Cueva", "Edison Flores", "Paolo Hurtado", "Sergio Pena", "Andy Polo", "Renato Tapia", "Yoshimar Yotun"]

        Faker().worldCup.rosters("Peru").forwards() // => ["Paolo Guerrero", "Andre Carrillo", "Raul Ruidiaz", "Jefferson Farfan"]

        Faker().worldCup.rosters("").coach() // => ["Stanislav Cherchesov"]
        ```
