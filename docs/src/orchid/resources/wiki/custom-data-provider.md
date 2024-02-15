---
---

# Custom Data Provider

Kotlin-faker allows you to easily create your own custom fake data generators (providers).

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

{% tip %}
If you want to support a different locale, place the file in `resources/locales/<locale>.json` instead
{% endtip %}

Then, we need to implement the `YamlFakeDataProvider` class:

```kotlin
class FooBar(fakerService: FakerService) : YamlFakeDataProvider<FooBar>(fakerService) {
    override val yamlCategory: YamlCategory = YamlCategory.CUSTOM // ❶
    override val secondaryCategory: Category = Category.ofName("FOOBAR") // ❷
    override val localUniqueDataProvider = LocalUniqueDataProvider<FooBar>()
    override val unique: FooBar by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory, secondaryCategory) // ❸
    }

    fun foo(): String = resolve(secondaryCategory, "foo")
    fun bar(): String = resolve(secondaryCategory, "bar")
    fun baz(): String = with(fakerService) { resolve(secondaryCategory, "baz").numerify().letterify() } // ❹
}
```

❶ The `yamlCategory` should always use `YamlCategory.CUSTOM` enum class
❷ If our json file is named `foobar.json`, the `secondaryKey` needs to override the category name
❸ In this case we also need to pass the `secondaryCategory` argument to `fakerService.load` function
❹ You have access to all default faker functionality, i.e. `FakerService#numerify` to replace all `#` placeholders with a random digit, and `FakerService#letterify` to replace all `?` with a random letter

Last, we need to add a very simple implementation of the `AbstractFaker`:

```kotlin
class CustomFaker(config: FakerConfig = fakerConfig { }) : AbstractFaker(config) {

    val fooBar by lazy { FooBar(fakerService) }
}
```

From there we can use our `CustomFaker` just like any other:

```kotlin
val testFaker = CustomFaker()
println(testFaker.fooBar.foo()) // baz
println(testFaker.fooBar.bar()) // foobar
println(testFaker.fooBar.baz()) // ABC 123
```
