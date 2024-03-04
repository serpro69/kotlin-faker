package io.github.serpro69.kfaker.databases

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.databases.provider.*

/**
 * Typealias for the [DatabasesFaker]
 */
typealias Faker = DatabasesFaker

@Suppress("unused")
class DatabasesFaker @JvmOverloads constructor(config: FakerConfig = fakerConfig { }) : AbstractFaker(config) {

    val databases: Databases by lazy { Databases(fakerService, randomService) }
    val mariaDB: MariaDB by lazy { MariaDB(fakerService) }
    val msSQLServer: MSSQLServer by lazy { MSSQLServer(fakerService) }
    val mySQL: MySQL by lazy { MySQL(fakerService) }
    val oracleDB: OracleDB by lazy { OracleDB(fakerService) }
    val postgreSQL: PostgreSQL by lazy { PostgreSQL(fakerService, config) }

    @FakerDsl
    /**
     * DSL builder for creating instances of [Faker]
     */
    class Builder internal constructor() : AbstractFaker.Builder<Faker>() {

        /**
         * Builds an instance of [Faker] with this [config].
         */
        override fun build(): Faker = Faker(config)
    }
}

/**
 * Applies the [block] function to [DatabasesFaker.Builder]
 * and returns as an instance of [DatabasesFaker] from that builder.
 */
fun faker(block: DatabasesFaker.Builder.() -> Unit): DatabasesFaker = DatabasesFaker.Builder().apply(block).build()