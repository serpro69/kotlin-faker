package io.github.sergio.igwt.kfaker

import io.github.sergio.igwt.kfaker.provider.Address
import io.github.sergio.igwt.kfaker.provider.Ancient
import io.github.sergio.igwt.kfaker.provider.Animal
import io.github.sergio.igwt.kfaker.provider.App
import io.github.sergio.igwt.kfaker.provider.Appliance
import io.github.sergio.igwt.kfaker.provider.AquaTeenHungerForce
import io.github.sergio.igwt.kfaker.provider.Artist
import io.github.sergio.igwt.kfaker.provider.BackToTheFuture
import io.github.sergio.igwt.kfaker.provider.Bank
import io.github.sergio.igwt.kfaker.provider.Basketball
import io.github.sergio.igwt.kfaker.provider.Beer
import io.github.sergio.igwt.kfaker.provider.BojackHoreseman
import io.github.sergio.igwt.kfaker.provider.Book
import io.github.sergio.igwt.kfaker.provider.BossaNova
import io.github.sergio.igwt.kfaker.provider.BreakingBad
import io.github.sergio.igwt.kfaker.provider.Buffy
import io.github.sergio.igwt.kfaker.provider.Business
import io.github.sergio.igwt.kfaker.provider.Cannabis
import io.github.sergio.igwt.kfaker.provider.Cat
import io.github.sergio.igwt.kfaker.provider.ChuckNorris
import io.github.sergio.igwt.kfaker.provider.Code
import io.github.sergio.igwt.kfaker.provider.Coffee
import io.github.sergio.igwt.kfaker.provider.Coin
import io.github.sergio.igwt.kfaker.provider.Color
import io.github.sergio.igwt.kfaker.provider.Commerce
import io.github.sergio.igwt.kfaker.provider.Community
import io.github.sergio.igwt.kfaker.provider.Company
import io.github.sergio.igwt.kfaker.provider.Name
import java.util.Locale

object Faker {
    private lateinit var fakerService: FakerService
    lateinit var address: Address
    lateinit var ancient: Ancient
    lateinit var animal: Animal
    lateinit var name: Name
    lateinit var app: App
    lateinit var appliance: Appliance
    lateinit var aquaTeenHungerForce: AquaTeenHungerForce
    lateinit var artist: Artist
    lateinit var backToTheFuture: BackToTheFuture
    lateinit var bank: Bank
    lateinit var basketball: Basketball
    lateinit var beer: Beer
    lateinit var bojackHoreseman: BojackHoreseman
    lateinit var book: Book
    lateinit var bossaNova: BossaNova
    lateinit var breakingBad: BreakingBad
    lateinit var buffy: Buffy
    lateinit var business: Business
    lateinit var cannabis: Cannabis
    lateinit var cat: Cat
    lateinit var chuckNorris: ChuckNorris
    lateinit var code: Code
    lateinit var coffee: Coffee
    lateinit var coin: Coin
    lateinit var color: Color
    lateinit var commerce: Commerce
    lateinit var community: Community
    lateinit var company: Company

    @JvmOverloads
    fun init(locale: Locale = Locale.ENGLISH): Faker {
        fakerService = FakerService(locale)

        address = Address(fakerService)
        ancient = Ancient(fakerService)
        animal = Animal(fakerService)
        name = Name(fakerService)
        app = App(fakerService)
        appliance = Appliance(fakerService)
        aquaTeenHungerForce = AquaTeenHungerForce(fakerService)
        artist = Artist(fakerService)
        backToTheFuture = BackToTheFuture(fakerService)
        bank = Bank(fakerService)
        basketball = Basketball(fakerService)
        beer = Beer(fakerService)
        bojackHoreseman = BojackHoreseman(fakerService)
        book = Book(fakerService)
        bossaNova = BossaNova(fakerService)
        breakingBad = BreakingBad(fakerService)
        buffy = Buffy(fakerService)
        business = Business(fakerService)
        cannabis = Cannabis(fakerService)
        cat = Cat(fakerService)
        chuckNorris = ChuckNorris(fakerService)
        code = Code(fakerService)
        coffee = Coffee(fakerService)
        coin = Coin(fakerService)
        color = Color(fakerService)
        commerce = Commerce(fakerService)
        community = Community(fakerService)
        company = Company(fakerService)

        return this
    }
}
