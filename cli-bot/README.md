<img src=../logo/faker_bot.png width=337 height=299 alt=""/>  

### Faker-Bot

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
The native images are available on [releases](https://github.com/serpro69/kotlin-faker/releases) page.  
Download the image to a desired location and make it executable or use below script (Depends on [jq](https://stedolan.github.io/jq/))

```
cd ~
wget $(curl -s https://api.github.com/repos/serpro69/kotlin-faker/releases/latest | jq -r '.assets[].browser_download_url') -O faker-bot
chmod a+x ~/faker-bot
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

#### `lookup` 
* `./faker-bot lookup name` - lookup functions by name (Case insensitive)

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

## Thanks
Inspired by [faker-ruby/faker-bot](https://github.com/faker-ruby/faker-bot)
