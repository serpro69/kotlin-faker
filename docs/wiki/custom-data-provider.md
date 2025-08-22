---
icon: fontawesome/solid/user-gear
---

# :fontawesome-solid-user-gear: Custom Data Generators

Do you have a very specific use-case for data generation? Kotlin-faker allows you to easily create your own custom fake data generators (providers), and even new fakers.

Let's assume you want to create a `FooBar` data generator with support for the default `en` locale.

First, we need to create a json file that will serve as your "data dictionary", e.g. `resources/locales/en/foobar.json`, with the following contents:

```json
{
  "en": {
    "faker": {
      "custom": {
        "foobar": {
          "foo": ["foo", "bar", "baz"],
          "bar": "foobar",
          "baz": "### ???"
        }
      }
    }
  }
}
```

- The first key in the json file should match the locale name.
- The second key should always be `faker` and the third `custom`
- The next key, `foobar` in this case, can be anything you want.
  - For `en` locale, the json filename should either match this key, or should be `custom.json`.
  - If the filename is `foobar.json`, we need to override the `secondaryCategory` property in the data generator implementation (see ❷ below), and pass this property as second argument to `fakerService.load` (see ❸ below).
- After that you have your data parameters with values as key-value pairs

!!! tip
    If you want to support a different locale, place the file in `resources/locales/<locale>/<category>.json` instead.

Then, we need to implement the `YamlFakeDataProvider` class:

=== "kotlin :material-language-kotlin:"
    ```kotlin
    class FooBar(fakerService: FakerService) : YamlFakeDataProvider<FooBar>(fakerService) {
        override val yamlCategory: YamlCategory = YamlCategory.CUSTOM // ❶
        override val secondaryCategory: Category = Category.ofName("FOOBAR") // ❷
        override val localUniqueDataProvider = LocalUniqueDataProvider<FooBar>()
        override val unique: FooBar by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

        init {
            fakerService.load(yamlCategory, secondaryCategory) // ❸
        }

        fun foo(): String = resolve(secondaryCategory, "foo")
        fun bar(): String = resolve(secondaryCategory, "bar")
        fun baz(): String = with(fakerService) { resolve(secondaryCategory, "baz").numerify().letterify() } // ❹
    }
    ```

❶ The `yamlCategory` should generally use `YamlCategory.CUSTOM` enum class. Although you could also use one of the existing categories if you like, one should be careful not to override existing functionality in a given data provider.
<br>
❷ If our json file is named `foobar.json`, the `secondaryKey` needs to override the category name
<br>
❸ In this case we also need to pass the `secondaryCategory` argument to `fakerService.load` function
<br>
❹ You have access to all default faker functionality, i.e. `FakerService#numerify` to replace all `#` placeholders with a random digit, and `FakerService#letterify` to replace all `?` with a random letter

!!! info
    It's totally understandable if you're confused about the naming of the `YamlFakeDataProvider` and `YamlCategory` classes, especially since we use json files to read data from.
    The reason for this is that the source of the data that faker uses comes in yml files, but faker converts them to json files at build time and uses those instead.

Last, we need to add a very simple implementation of the `AbstractFaker`:

=== "kotlin :material-language-kotlin:"
    ```kotlin
    class CustomFaker(config: FakerConfig = fakerConfig { }) : AbstractFaker(config) {

        val fooBar by lazy { FooBar(fakerService) }
    }
    ```

From there we can use our `CustomFaker` just like any other:

=== "kotlin :material-language-kotlin:"
    ```kotlin
    val testFaker = CustomFaker()
    println(testFaker.fooBar.foo()) // baz
    println(testFaker.fooBar.bar()) // foobar
    println(testFaker.fooBar.baz()) // ABC 123
    ```
