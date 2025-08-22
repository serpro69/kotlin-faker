---
title: internet
faker: core
---

## `Faker().internet`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/internet.yml:internet_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    // "Safe" internet domain
    Faker().internet.domain() // => cartwright-lesch.test
    Faker().internet.domain(subdomain = true) // => mraz.mills.test
    Faker().internet.domain(domain = "foo") // => foo.test
    Faker().internet.domain(subdomain = true, domain = "bar") // => doyle.bar.test
    Faker().internet.domain(domain = "kotlin-faker.test") // => kotlin-faker.test

    // IPv4 addresses, public and private
    Faker().internet.privateIPv4Address() // => 192.168.82.243
    Faker().internet.publicIPv4Address() // => 25.162.239.133
    Faker().internet.iPv4Address() // => 230.168.182.77

    // IPv6 addresses
    Faker().internet.iPv6Address() // => 176f:cfec:c73b:e0cb:534d:4b3e:db4e:3b53

    // Mac addresses
    Faker().internet.macAddress() // => 17:12:d9:fc:fe:f6
    Faker().internet.macAddress("a") // => 0a:11:ed:7c:b5:af
    Faker().internet.macAddress("aa") // => aa:ec:eb:54:b9:f5
    Faker().internet.macAddress("aa:ce") // => aa:ce:e3:e1:83:c4

    Faker().internet.email() // les.weissnat@gmail.com
    Faker().internet.email("john.doe") // => john.doe@gmail.com

    // Generates an RFC 2606 compliant fake email with a `test` domain suffix, which means it will never deliver successfully
    Faker().internet.safeEmail() // les.weissnat@gmail.test
    Faker().internet.safeEmail("jane.doe") // => jane.doe@yahoo.test

    Faker().internet.slug() // => report

    Faker().internet.domainSuffix() // => com

    // Random user agent by browser type (case-insensitive)
    Faker().internet.userAgent("firefox") // => Mozilla/5.0 (Windows NT x.y; Win64; x64; rv:10.0) Gecko/20100101 Firefox/10.0
    // or by a random browser type
    Faker().internet.userAgent("") // => Opera/9.80 (X11; Linux i686; Ubuntu/14.10) Presto/2.12.388 Version/12.16

    // Random bot user agent by type (case-insensitive)
    Faker().botUserAgent("duckduckbot") // => DuckDuckBot-Https/1.1; (+https://duckduckgo.com/duckduckbot)
    // or by a random type
    Faker().botUserAgent("") // => Mozilla/5.0 AppleWebKit/537.36 (KHTML, like Gecko; compatible; Googlebot/2.1; +http://www.google.com/bot.html) Chrome/83.0.4103.122 Safari/537.36
    ```
