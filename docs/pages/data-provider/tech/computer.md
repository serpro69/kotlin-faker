---
title: computer
faker: tech
---

## `Faker().computer`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/computer.yml"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().computer.type() // => server
    Faker().computer.platform() // => Linux
    Faker().computer.os.linux() // Ubuntu Server 22.04
    Faker().computer.os.openBsd() = resolve("os", "openbsd") // => OpenBSD 6
    Faker().computer.os.templeOS() = resolve("os", "templeos") // => TempleOS 5.03
    Faker().computer.os.plan9() = resolve("os", "plan 9") // => Plan 9 Fourth Edition
    Faker().computer.os.macOS() = resolve("os", "macos") // High Sierra (10.13)
    Faker().computer.os.windows() = resolve("os", "windows") // Windows 7
    ```
