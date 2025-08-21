---
icon: material/hand-extended
---

# :material-hand-extended: Big List of Naughty Strings Extension

## About

Kotlin-faker `blns` artifact provides convenience functions for returning strings from [The Big List of Naughty Strings](https://github.com/minimaxir/big-list-of-naughty-strings) - a list of strings which have a high probability of causing issues when used as user-input data, and can therefore be quite useful in testing.

## Disclaimer

> The Big List of Naughty Strings is intended to be used for _software you own and manage_. Some of the Naughty Strings can indicate security vulnerabilities, and as a result using such strings with third-party software may be a crime. The maintainer is not responsible for any negative actions that result from the use of the list.
>
> Additionally, the Big List of Naughty Strings is not a fully-comprehensive substitute for formal security/penetration testing for your service.

## Usage

### Installation

- ① add the core `kotlin-faker` dependency to the test classpath
- ② then add the dependency for the `kotlin-faker-blns` extension

=== "gradle :simple-gradle:"
    ```kotlin
    dependencies {
      testImplementation("io.github.serpro69:kotlin-faker:$fakerVersion") // ①
      testImplementation("io.github.serpro69:kotlin-faker-blns:$fakerVersion") // ②
    }
    ```

### Using the Big List of Naughty Strings

The `Blns` class provides properties and functions to get all strings, as well as a sublist of strings, and a single random string.

There is also corresponding functionality for getting base64-encoded strings.

For example, using [JUnit5 Parameterized Testing](https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests) capabilities:

- ① Create an instance of `Blns` class
- ② Get `all` strings
- ③ Get a `sublist` of strings
- ④ Get a `random` string
- ⑤ `get` strings by a `Category`
- ⑥ Test your inputs
- Profit 💸

=== "kotlin :material-language-kotlin:"
    ```kotlin
    class Test {
      @ParameterizedTest
      @MethodSource("allStrings") // ⑥
      fun `test input with a naughty string`(s: String) {
        inputField.sendKeys(s) // ⑥
      }

      companion object {
        private val blns = blns { /*faker configuration*/ } // ① 
        @JvmStatic private fun allStrings() = blns.all.stream() // ②
        @JvmStatic private fun allBase64 () = blns.allBase64.stream() // ②
        @JvmStatic private fun sublist() = blns.sublist(10).stream() // ③
        @JvmStatic private fun sublistBase64() = blns.sublist(10, base64 = true).stream() // ③
        val randomString: String get() = blns.random() // ④
        val randomBase64String: String get() = blns.random(base64 = true) // ④
        val emojiStrings = blns.get(Category.EMOJI) // ⑤
        val emojiAndKaomojiStrings = blns.get(Category.EMOJI, Category.KAOMOJI) // ⑤
        val basicCategories = blns.get(Category.RESERVED, Category.NUMERIC, Category.SPECIAL) // ⑤
      }
    }
    ```

## Credits

The input for this extension is maintained by [minimaxir](https://github.com/minimaxir) at [minimaxir/big-list-of-naughty-strings](https://github.com/minimaxir/big-list-of-naughty-strings).
