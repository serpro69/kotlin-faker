---
title: music
---

## `Faker().music`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/music.yml:music_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().music.instruments() // => Electric Guitar

    Faker().music.bands() // => AC/DC

    Faker().music.albums() // => Thriller

    Faker().music.genres() // => Rock

    Faker().music.mamboNo5 // => Angela

    Faker().music.hipHop.subgenres() // => Low Fi

    Faker().music.hipHop.groups() // => Outcast

    Faker().music.hipHop.artist() // => Drake
    ```
