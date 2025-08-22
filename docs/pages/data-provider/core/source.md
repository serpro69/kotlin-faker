---
title: source
faker: core
---

## `Faker().source`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/source.yml:source_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    ```

---

??? info "non-implemented functions"
    === "kotlin :material-language-kotlin:"
        ```kotlin
        Faker().source.hello_world("ruby") // => puts 'Hello World!'

        Faker().source.hello_world("javascript") // => alert('Hello World!');

        Faker().source.print("ruby") // => "puts 'faker_string_to_print'"

        Faker().source.print("javascript") // => "console.log('faker_string_to_print');"

        Faker().source.print_1_to_10("ruby") // => (()1..10).each { |i| puts i }

        Faker().source.print_1_to_10("javascript")  // => for (let i=0; i<10; i++) {
                                                    //        console.log(i);
                                                    //    }
        ```
