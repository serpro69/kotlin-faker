---
title: opera
faker: music
---

## `Faker().opera`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/jvmMain/resources/locales/en/opera.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    // Italian Opera
    Faker().opera.italian.byGiuseppeVerdi() // => Otello
    Faker().opera.italian.byGioacchinoRossini() // => Demetrio e Polibio
    Faker().opera.italian.byGaetanoDonizetti() // => Olimpiade
    Faker().opera.italian.byVincenzoBellini() // => Il pirata
    Faker().opera.italian.byChristophWillibaldGluck() // => Artaserse
    Faker().opera.italian.byWolfgangAmadeusMozart() // => Cosi fan tutte

    // German Opera
    Faker().opera.german.byWolfgangAmadeusMozart() // => Bastien und Bastienne
    Faker().opera.german.byLudwigVanBeethoven() // => Fidelio
    Faker().opera.german.byCarlMariaVonWeber() // => Silvana
    Faker().opera.german.byRichardStrauss() // => Guntram
    Faker().opera.german.byRichardWagner() // => Die Feen
    Faker().opera.german.byRobertSchumann() // => Genoveva
    Faker().opera.german.byFranzSchubert() // => Sakuntala
    Faker().opera.german.byAlbanBerg() // => Wozzeck

    // French Opera
    Faker().opera.french.byChristophWillibaldGluck() // => La fausse esclave
    Faker().opera.french.byMauriceRavel() // => L'heure espagnole
    Faker().opera.french.byHectorBerlioz() // => Benvenuto Cellini
    Faker().opera.french.byGeorgesBizet() // => La maison du docteur
    Faker().opera.french.byCharlesGounod() // => Sapho
    Faker().opera.french.byCamilleSaintSaens() // => L'ancêtre
    ```
