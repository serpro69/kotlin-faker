---
title: file
---

## `Faker().file`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/file.yml:file_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().file.extension() // => flac
    Faker().file.mimeType.application() // => application/atom+xml
    Faker().file.mimeType.audio() // => audio/basic
    Faker().file.mimeType.image() // => image/gif
    Faker().file.mimeType.message() // => message/http
    Faker().file.mimeType.model() // => model/example
    Faker().file.mimeType.multipart() // => multipart/mixed
    Faker().file.mimeType.text() // => text/cmd
    Faker().file.mimeType.video() // => video/mpeg
    ```
