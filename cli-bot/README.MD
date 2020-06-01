<img src=../logo/faker_bot.png width=337 height=299 alt=""/>  

# Faker-Bot

A CLI application that helps to quickly find required functions of kotlin-faker

<img src=./img/faker-peek.gif width="768" height="606" alt=""/>

## Installation
The native images are available on [releases](https://github.com/serpro69/kotlin-faker/releases) page.  
<i> Note that not every release will have a corresponding native image. In most cases only major/minor version
releases will contain a release of this application, as they are likely to have new functionality
or changes.</i>

## Usage
Usage details are also available with the `--help` option:  
`./faker-bot --help`  
`./faker-bot list --help`  
`./faker-bot lookup --help`

### Available commands
#### `list` - List available providers and their functions

#### `lookup` - Lookup functions by name (Case insensitive)

### Available options
#### Verbose output
`./faker-bot list --verbose` - prints sample values for each function
`./faker-bot lookup name --verbose` - prints sample values for each function

#### Switch to java syntax
`./faker-bot list --java-syntax` - prints list of all available providers and their functions using java syntax

#### Using non-default locale
`./faker-bot list --verbose --locale de` - all localized functions with be printed using `de` locale.

#### List available locales
`./faker-bot list --list-locales` - prints all available locales
