package io.github.serpro69.kfaker.dictionary

/**
 * Represents a collection of [categories] (i.e. `address`, `book`, etc.)
 */
internal data class Dictionary(val categories: List<Category>)

/**
 * Represents a single category that has a [categoryName] (i.e. `address`) and all [values] in this category
 */
internal data class Category(val categoryName: CategoryName, val values: Map<String, *>)

/**
 * This enum contains all default categories and matches with the names of the .yml files for 'en' locale.
 *
 * If any new category is added to .yml file(s) a new class has to be added to this enum as well.
 */
internal enum class CategoryName {
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
    YODA
}

@Suppress("EXPERIMENTAL_FEATURE_WARNING")
@JvmInline
internal value class RawExpression(val value: String)

/**
 * Returns [CategoryName] by [name] string (case-insensitive).
 */
internal fun getCategoryName(name: String) = CategoryName.values().first { it.toLowerCase() == name.lowercase() }

internal fun CategoryName.toLowerCase() = this.name.lowercase()

/**
 * Gets [Category] by its [name] from this [Dictionary].
 */
internal fun Dictionary.getCategoryByName(name: String): Category {
    return this.getCategoryByName(categoryName = getCategoryName(name))
}

/**
 * Gets [Category] by its [categoryName] from this [Dictionary].
 */
internal fun Dictionary.getCategoryByName(categoryName: CategoryName): Category {
    return this.categories.firstOrNull { it.categoryName.toLowerCase() == categoryName.toLowerCase() }
        ?: throw NoSuchElementException("Category with name $categoryName not found")
}
