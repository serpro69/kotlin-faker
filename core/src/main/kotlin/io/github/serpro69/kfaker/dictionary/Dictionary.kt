package io.github.serpro69.kfaker.dictionary

/**
 * Represents a collection of [entries] (i.e. `address`, `book`, etc.)
 */
internal data class Dictionary(val entries: List<DictEntry>)

/**
 * Represents a single category that has a [category] (i.e. `address`) and all [values] in this category
 */
internal data class DictEntry(val category: YamlCategory, val values: Map<String, *>)

/**
 * This enum contains all default categories and matches with the names of the .yml files for 'en' locale.
 *
 * If any new category is added to .yml file(s) a new class has to be added to this enum as well.
 */
internal enum class YamlCategory : Category {
    // Special providers for locale-based symbols
    SEPARATOR,
    CURRENCY_SYMBOL,
    // Rest of providers
    ADDRESS,
    ANCIENT,
    APP,
    APPLIANCE,
    AQUA_TEEN_HUNGER_FORCE,
    ARTIST,
    BACK_TO_THE_FUTURE,
    BANK,
    BARCODE,
    BASKETBALL,
    BEER,
    BIG_BANG_THEORY,
    BLOOD,
    BOJACK_HORSEMAN,
    BOOK,
    BOSSA_NOVA,
    BREAKING_BAD,
    BUFFY,
    BUSINESS,
    CANNABIS,
    CELL_PHONE,
    CHIQUITO,
    CHUCK_NORRIS,
    CODE,
    COFFEE,
    COIN,
    COLOR,
    COMMERCE,
    COMMUNITY,
    COMPANY,
    COMPASS,
    COMPUTER,
    CONSTRUCTION,
    COSMERE,
    COUNTRY_CODE,
    CREATURE,
    CROSSFIT,
    CRYPTO_COIN,
    CULTURE_SERIES,
    CURRENCY,
    DC_COMICS,
    DEMOGRAPHIC,
    DEPARTED,
    DESSERT,
    DEVICE,
    DND,
    GAMES,
    DRAGON_BALL,
    DRIVING_LICENCE,
    DRONE,
    DR_WHO,
    DUMB_AND_DUMBER,
    DUNE,
    EDUCATOR,
    ELECTRICAL_COMPONENTS,
    ESPORT,
    FAMILY_GUY,
    FILE,
    FINANCE,
    FOOD,
    FOOTBALL,
    FRIENDS,
    FUNNY_NAME,
    FUTURAMA,
    GAME,
    GAME_OF_THRONES,
    GENDER,
    GHOSTBUSTERS,
    GRATEFUL_DEAD,
    GREEK_PHILOSOPHERS,
    HACKER,
    HARRY_POTTER,
    HEROES,
    HEROES_OF_THE_STORM,
    HEY_ARNOLD,
    HIPSTER,
    HITCHHIKERS_GUIDE_TO_THE_GALAXY,
    HOBBIT,
    HOUSE,
    HOW_I_MET_YOUR_MOTHER,
    ID_NUMBER,
    INDUSTRY_SEGMENTS,
    INTERNET,
    INVOICE,
    JOB,
    KPOP,
    LEBOWSKI,
    LORD_OF_THE_RINGS,
    LOREM,
    LOVECRAFT,
    MARKDOWN,
    MARKETING,
    MEASUREMENT,
    MICHAEL_SCOTT,
    MILITARY,
    MONEY,
    MOVIE,
    MUSIC,
    NAME,
    NATION,
    NATO_PHONETIC_ALPHABET,
    NEW_GIRL,
    ONE_PIECE,
    OPERA,
    PARKS_AND_REC,
    PEARL_JAM,
    PHISH,
    PHONE_NUMBER,
    PRINCE,
    PRINCESS_BRIDE,
    PROGRAMMING_LANGUAGE,
    QUOTE,
    RAJNIKANTH,
    RELATIONSHIP,
    RESTAURANT,
    RICK_AND_MORTY,
    ROCK_BAND,
    RUPAUL,
    RUSH,
    SCIENCE,
    SEINFELD,
    SHAKESPEARE,
    SHOW,
    SILICON_VALLEY,
    SIMPSONS,
    SLACK_EMOJI,
    SOURCE,
    SOUTH_PARK,
    SPACE,
    STARGATE,
    STAR_TREK,
    STAR_WARS,
    STRANGER_THINGS,
    STRIPE,
    SUBSCRIPTION,
    SUITS,
    SUPERHERO,
    SWORD_ART_ONLINE,
    TEAM,
    THE_EXPANSE,
    THE_FRESH_PRINCE_OF_BEL_AIR,
    THE_IT_CROWD,
    THE_THICK_OF_IT,
    TWIN_PEAKS,
    UMPHREYS_MCGEE,
    UNIVERSITY,
    VEHICLE,
    VENTURE_BROS,
    VERBS,
    V_FOR_VENDETTA,
    WORLD_CUP,
    YODA,
    ;

    companion object {

        /**
         * Returns [YamlCategory] by [name] string (case-insensitive).
         */
        internal fun findByName(name: String): YamlCategory {
            return values().firstOrNull { it.lowercase() == name.lowercase() }
                ?: throw NoSuchElementException("Category with name '$name' not found.")
        }
    }
}

@Suppress("EXPERIMENTAL_FEATURE_WARNING")
@JvmInline
internal value class RawExpression(val value: String)

internal fun Category.lowercase() = name.lowercase()
