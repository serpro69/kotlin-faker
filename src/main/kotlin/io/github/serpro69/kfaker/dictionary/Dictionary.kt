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
    SEPARATOR,
    ADDRESS,
    ANCIENT,
    CREATURE,
    APP,
    APPLIANCE,
    AQUA_TEEN_HUNGER_FORCE,
    ARTIST,
    BACK_TO_THE_FUTURE,
    BANK,
    BASKETBALL,
    BEER,
    BOJACK_HORSEMAN,
    BOOK,
    BOSSA_NOVA,
    BREAKING_BAD,
    BUFFY,
    BUSINESS,
    CANNABIS,
    CHUCK_NORRIS,
    CODE,
    COFFEE,
    COIN,
    COLOR,
    COMMERCE,
    COMMUNITY,
    COMPANY,
    COMPASS,
    CONSTRUCTION,
    COSMERE,
    CRYPTO_COIN,
    CULTURE_SERIES,
    CURRENCY,
    DC_COMICS,
    DEMOGRAPHIC,
    DESSERT,
    DEVICE,
    GAMES,
    DRAGON_BALL,
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
    THE_FRESH_PRINCE_OF_BEL_AIR,
    FRIENDS,
    FUNNY_NAME,
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
    MOVIE,
    MUSIC,
    NAME,
    NATION,
    NATO_PHONETIC_ALPHABET,
    NEW_GIRL,
    ONE_PIECE,
    OPERA,
    PARKS_AND_REC,
    PHISH,
    PHONE_NUMBER,
    CELL_PHONE,
    COUNTRY_CODE,
    PRINCESS_BRIDE,
    PROGRAMMING_LANGUAGE,
    QUOTE,
    RELATIONSHIP,
    RESTAURANT,
    RICK_AND_MORTY,
    ROCK_BAND,
    RUPAUL,
    SCIENCE,
    SEINFELD,
    SHAKESPEARE,
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
    SUPERHERO,
    SWORD_ART_ONLINE,
    TEAM,
    THE_EXPANSE,
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
internal inline class RawExpression(val value: String)

/**
 * Returns [CategoryName] by [name] string (case-insensitive).
 */
internal fun getCategoryName(name: String) = CategoryName.values().first { it.toLowerCase() == name.toLowerCase() }

internal fun CategoryName.toLowerCase() = this.name.toLowerCase()

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
