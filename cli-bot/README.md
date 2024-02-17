<a href="https://github.com/serpro69/kotlin-faker"> <img src=../logo/faker_bot.png alt="faker-bot"/> </a>  

Quickly find required kotlin-faker functionality from your terminal

<img src=./img/faker-peek.gif width="768" height="606" alt=""/>

## ToC

* [Installation](#installation)
* [Usage](#usage)
    * [Available commands](#available-commands)
        * [list](#list)
        * [lookup](#lookup)
    * [Available options](#available-options)
        * [--verbose](#verbose-output)
        * [--java-syntax](#switch-to-java-syntax)
        * [--locale](#using-non-default-locale)
        * [--list-locales](#list-available-locales)
* [Thanks](#thanks)

## Installation

Zero-dependencies native images are available on [releases](https://github.com/serpro69/kotlin-faker/releases) page.  
Download the image to a desired location and make it executable or use below script (Depends on [jq](https://stedolan.github.io/jq/))

```bash
cd ~
wget $(curl -s https://api.github.com/repos/serpro69/kotlin-faker/releases/latest | jq -r '.assets[].browser_download_url') -O faker-bot
chmod a+x ~/faker-bot
``` 

If you prefer a .jar instead of a native image, these are also available for each release. Just download the jar and run it with `java -jar`:

```bash
java -jar ./faker-bot.jar --help
```

## Usage

Usage details are also available with the `--help` option:  

`./faker-bot --help`  
`./faker-bot list --help`  
`./faker-bot lookup --help`

### Available commands

#### `list`

* `./faker-bot list` - list all providers and their functions
* `./faker-bot list Address Name` - list functions of `Address` and `Name` providers (Case insensitive)

Since version `1.4.0` partial matching is also supported:

* `./faker-bot list addr` - list functions of `Address` provider

#### `lookup`

* `./faker-bot lookup name` - lookup providers and functions by name (Case insensitive partial matching)

### Available options

#### Verbose output

`./faker-bot list --verbose` - prints sample values for each function
`./faker-bot lookup name --verbose` - prints sample values for each function

#### Switch to java syntax

`./faker-bot list --java-syntax` - prints list of all available providers and their functions using java syntax

#### Using non-default locale

`./faker-bot list --verbose --locale de` - combined with `--verbose`, all localized functions will be printed
using `de` locale.

#### List available locales

`./faker-bot list --list-locales` - prints all available locales

#### List available fakers

`./faker-bot list --list-fakers` - prints all available faker implementations

## Thanks

Inspired by [faker-ruby/faker-bot](https://github.com/faker-ruby/faker-bot)
