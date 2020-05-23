package io.github.serpro69.kfaker.app.subcommands

import io.github.serpro69.kfaker.app.*
import picocli.*

@CommandLine.Command(
    name = "address"
)
object Address : SubCommand() {
    @CommandLine.Spec
    override lateinit var spec: CommandLine.Model.CommandSpec

    @CommandLine.ParentCommand
    override lateinit var parent: KFaker

    @CommandLine.Command(name = "country")
    fun country() {
        super.run()
        println(faker.address.country())
    }

/*
    @CommandLine.Command(name = "countryByCode")
    fun countryByCode(countryCode: String) {
        super.run()

        println(faker.address.countryByCode())
    }
*/

/*
    @CommandLine.Command(name = "countryByName")
    fun countryByName(countryName: String) {
        super.run()
        println(faker.address.countryByName())
    }
*/

    @CommandLine.Command(name = "countryCode")
    fun countryCode() {
        super.run()
        println(faker.address.countryCode())
    }

    @CommandLine.Command(name = "countryCodeLong")
    fun countryCodeLong() {
        super.run()
        println(faker.address.countryCodeLong())
    }

    @CommandLine.Command(name = "buildingNumber")
    fun buildingNumber() {
        super.run()
        println(faker.address.buildingNumber())
    }

    @CommandLine.Command(name = "community")
    fun community() {
        super.run()
        println(faker.address.community())
    }

    @CommandLine.Command(name = "secondaryAddress")
    fun secondaryAddress() {
        super.run()
        println(faker.address.secondaryAddress())
    }

    @CommandLine.Command(name = "postcode")
    fun postcode() {
        super.run()
        println(faker.address.postcode())
    }

/*
    @CommandLine.Command(name = "postcodeByState")
    fun postcodeByState(state: String) {
        super.run()
        println(faker.address.postcodeByState())
    }
*/

    @CommandLine.Command(name = "state")
    fun state() {
        super.run()
        println(faker.address.state())
    }


    @CommandLine.Command(name = "stateAbbr")
    fun stateAbbr() {
        super.run()
        println(faker.address.stateAbbr())
    }

    @CommandLine.Command(name = "timeZone")
    fun timeZone() {
        super.run()
        println(faker.address.timeZone())
    }

    @CommandLine.Command(name = "city")
    fun city() {
        super.run()
        println(faker.address.city())
    }

    @CommandLine.Command(name = "streetName")
    fun streetName() {
        super.run()
        println(faker.address.streetName())
    }

    @CommandLine.Command(name = "streetAddress")
    fun streetAddress() {
        super.run()
        println(faker.address.streetAddress())
    }

    @CommandLine.Command(name = "fullAddress")
    fun fullAddress() {
        super.run()
        println(faker.address.fullAddress())
    }

    @CommandLine.Command(name = "defaultCountry")
    fun defaultCountry() {
        super.run()
        println(faker.address.defaultCountry())
    }
}
