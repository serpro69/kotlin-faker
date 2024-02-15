@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package io.github.serpro69.kfaker

import io.github.serpro69.kfaker.provider.Address
import io.github.serpro69.kfaker.provider.Adjective
import io.github.serpro69.kfaker.provider.Airport
import io.github.serpro69.kfaker.provider.Ancient
import io.github.serpro69.kfaker.provider.Animal
import io.github.serpro69.kfaker.provider.App
import io.github.serpro69.kfaker.provider.Appliance
import io.github.serpro69.kfaker.provider.AquaTeenHungerForce
import io.github.serpro69.kfaker.provider.Archer
import io.github.serpro69.kfaker.provider.Artist
import io.github.serpro69.kfaker.provider.Australia
import io.github.serpro69.kfaker.provider.Avatar
import io.github.serpro69.kfaker.provider.BackToTheFuture
import io.github.serpro69.kfaker.provider.Bank
import io.github.serpro69.kfaker.provider.Barcode
import io.github.serpro69.kfaker.provider.Basketball
import io.github.serpro69.kfaker.provider.Beer
import io.github.serpro69.kfaker.provider.Bible
import io.github.serpro69.kfaker.provider.BigBangTheory
import io.github.serpro69.kfaker.provider.Bird
import io.github.serpro69.kfaker.provider.Blood
import io.github.serpro69.kfaker.provider.BojackHorseman
import io.github.serpro69.kfaker.provider.BossaNova
import io.github.serpro69.kfaker.provider.BreakingBad
import io.github.serpro69.kfaker.provider.BrooklynNineNine
import io.github.serpro69.kfaker.provider.Buffy
import io.github.serpro69.kfaker.provider.Business
import io.github.serpro69.kfaker.provider.Camera
import io.github.serpro69.kfaker.provider.Cannabis
import io.github.serpro69.kfaker.provider.Cat
import io.github.serpro69.kfaker.provider.Chess
import io.github.serpro69.kfaker.provider.Chiquito
import io.github.serpro69.kfaker.provider.ChuckNorris
import io.github.serpro69.kfaker.provider.Code
import io.github.serpro69.kfaker.provider.Coffee
import io.github.serpro69.kfaker.provider.Coin
import io.github.serpro69.kfaker.provider.Color
import io.github.serpro69.kfaker.provider.Commerce
import io.github.serpro69.kfaker.provider.Community
import io.github.serpro69.kfaker.provider.Company
import io.github.serpro69.kfaker.provider.Computer
import io.github.serpro69.kfaker.provider.Conan
import io.github.serpro69.kfaker.provider.Construction
import io.github.serpro69.kfaker.provider.Cosmere
import io.github.serpro69.kfaker.provider.CowboyBebop
import io.github.serpro69.kfaker.provider.Crossfit
import io.github.serpro69.kfaker.provider.CryptoCoin
import io.github.serpro69.kfaker.provider.Currency
import io.github.serpro69.kfaker.provider.CurrencySymbol
import io.github.serpro69.kfaker.provider.DcComics
import io.github.serpro69.kfaker.provider.Demographic
import io.github.serpro69.kfaker.provider.Departed
import io.github.serpro69.kfaker.provider.Dessert
import io.github.serpro69.kfaker.provider.Device
import io.github.serpro69.kfaker.provider.Dog
import io.github.serpro69.kfaker.provider.Doraemon
import io.github.serpro69.kfaker.provider.DrWho
import io.github.serpro69.kfaker.provider.DragonBall
import io.github.serpro69.kfaker.provider.DrivingLicense
import io.github.serpro69.kfaker.provider.Drone
import io.github.serpro69.kfaker.provider.DumbAndDumber
import io.github.serpro69.kfaker.provider.ESport
import io.github.serpro69.kfaker.provider.Educator
import io.github.serpro69.kfaker.provider.ElectricalComponents
import io.github.serpro69.kfaker.provider.Emotion
import io.github.serpro69.kfaker.provider.FamilyGuy
import io.github.serpro69.kfaker.provider.File
import io.github.serpro69.kfaker.provider.FinalSpace
import io.github.serpro69.kfaker.provider.Finance
import io.github.serpro69.kfaker.provider.FmaBrotherhood
import io.github.serpro69.kfaker.provider.Food
import io.github.serpro69.kfaker.provider.Football
import io.github.serpro69.kfaker.provider.FreshPriceOfBelAir
import io.github.serpro69.kfaker.provider.Friends
import io.github.serpro69.kfaker.provider.FunnyName
import io.github.serpro69.kfaker.provider.Futurama
import io.github.serpro69.kfaker.provider.GameOfThrones
import io.github.serpro69.kfaker.provider.Gender
import io.github.serpro69.kfaker.provider.GhostBusters
import io.github.serpro69.kfaker.provider.GratefulDead
import io.github.serpro69.kfaker.provider.GreekPhilosophers
import io.github.serpro69.kfaker.provider.Hacker
import io.github.serpro69.kfaker.provider.Hackers
import io.github.serpro69.kfaker.provider.HarryPotter
import io.github.serpro69.kfaker.provider.HeyArnold
import io.github.serpro69.kfaker.provider.Hipster
import io.github.serpro69.kfaker.provider.HitchhikersGuideToTheGalaxy
import io.github.serpro69.kfaker.provider.Hobbit
import io.github.serpro69.kfaker.provider.Hobby
import io.github.serpro69.kfaker.provider.Horse
import io.github.serpro69.kfaker.provider.House
import io.github.serpro69.kfaker.provider.HowIMetYourMother
import io.github.serpro69.kfaker.provider.HowToTrainYourDragon
import io.github.serpro69.kfaker.provider.IdNumber
import io.github.serpro69.kfaker.provider.IndustrySegments
import io.github.serpro69.kfaker.provider.Internet
import io.github.serpro69.kfaker.provider.JackHandey
import io.github.serpro69.kfaker.provider.Job
import io.github.serpro69.kfaker.provider.KPop
import io.github.serpro69.kfaker.provider.KamenRider
import io.github.serpro69.kfaker.provider.Lebowski
import io.github.serpro69.kfaker.provider.LordOfTheRings
import io.github.serpro69.kfaker.provider.Lorem
import io.github.serpro69.kfaker.provider.Markdown
import io.github.serpro69.kfaker.provider.Marketing
import io.github.serpro69.kfaker.provider.Measurement
import io.github.serpro69.kfaker.provider.MichaelScott
import io.github.serpro69.kfaker.provider.Military
import io.github.serpro69.kfaker.provider.MitchHedberg
import io.github.serpro69.kfaker.provider.Money
import io.github.serpro69.kfaker.provider.Mountain
import io.github.serpro69.kfaker.provider.Mountaineering
import io.github.serpro69.kfaker.provider.Movie
import io.github.serpro69.kfaker.provider.Music
import io.github.serpro69.kfaker.provider.Name
import io.github.serpro69.kfaker.provider.Naruto
import io.github.serpro69.kfaker.provider.Nation
import io.github.serpro69.kfaker.provider.NatoPhoneticAlphabet
import io.github.serpro69.kfaker.provider.NewGirl
import io.github.serpro69.kfaker.provider.OnePiece
import io.github.serpro69.kfaker.provider.Opera
import io.github.serpro69.kfaker.provider.ParksAndRec
import io.github.serpro69.kfaker.provider.PearlJam
import io.github.serpro69.kfaker.provider.Person
import io.github.serpro69.kfaker.provider.Phish
import io.github.serpro69.kfaker.provider.PhoneNumber
import io.github.serpro69.kfaker.provider.Prince
import io.github.serpro69.kfaker.provider.PrincessBride
import io.github.serpro69.kfaker.provider.ProgrammingLanguage
import io.github.serpro69.kfaker.provider.Quote
import io.github.serpro69.kfaker.provider.Rajnikanth
import io.github.serpro69.kfaker.provider.Relationship
import io.github.serpro69.kfaker.provider.Restaurant
import io.github.serpro69.kfaker.provider.RickAndMorty
import io.github.serpro69.kfaker.provider.RockBand
import io.github.serpro69.kfaker.provider.Room
import io.github.serpro69.kfaker.provider.Rupaul
import io.github.serpro69.kfaker.provider.Rush
import io.github.serpro69.kfaker.provider.Science
import io.github.serpro69.kfaker.provider.Seinfeld
import io.github.serpro69.kfaker.provider.Separator
import io.github.serpro69.kfaker.provider.Shakespeare
import io.github.serpro69.kfaker.provider.Show
import io.github.serpro69.kfaker.provider.SiliconValley
import io.github.serpro69.kfaker.provider.Simpsons
import io.github.serpro69.kfaker.provider.SlackEmoji
import io.github.serpro69.kfaker.provider.SmashingPumpkins
import io.github.serpro69.kfaker.provider.SouthPark
import io.github.serpro69.kfaker.provider.Space
import io.github.serpro69.kfaker.provider.Spongebob
import io.github.serpro69.kfaker.provider.Sport
import io.github.serpro69.kfaker.provider.StarTrek
import io.github.serpro69.kfaker.provider.StarWars
import io.github.serpro69.kfaker.provider.Stargate
import io.github.serpro69.kfaker.provider.StrangerThings
import io.github.serpro69.kfaker.provider.Stripe
import io.github.serpro69.kfaker.provider.StudioGhibli
import io.github.serpro69.kfaker.provider.Subscription
import io.github.serpro69.kfaker.provider.Suits
import io.github.serpro69.kfaker.provider.Superhero
import io.github.serpro69.kfaker.provider.Supernatural
import io.github.serpro69.kfaker.provider.SwordArtOnline
import io.github.serpro69.kfaker.provider.Tarkov
import io.github.serpro69.kfaker.provider.Tea
import io.github.serpro69.kfaker.provider.Team
import io.github.serpro69.kfaker.provider.TheExpanse
import io.github.serpro69.kfaker.provider.TheITCrowd
import io.github.serpro69.kfaker.provider.TheOffice
import io.github.serpro69.kfaker.provider.TheRoom
import io.github.serpro69.kfaker.provider.TheThickOfIt
import io.github.serpro69.kfaker.provider.Tolkien
import io.github.serpro69.kfaker.provider.TrainStation
import io.github.serpro69.kfaker.provider.Tron
import io.github.serpro69.kfaker.provider.TwinPeaks
import io.github.serpro69.kfaker.provider.UmphreysMcgee
import io.github.serpro69.kfaker.provider.University
import io.github.serpro69.kfaker.provider.VForVendetta
import io.github.serpro69.kfaker.provider.Vehicle
import io.github.serpro69.kfaker.provider.VentureBros
import io.github.serpro69.kfaker.provider.Verbs
import io.github.serpro69.kfaker.provider.Volleyball
import io.github.serpro69.kfaker.provider.WorldCup
import io.github.serpro69.kfaker.provider.Yoda
import io.github.serpro69.kfaker.provider.misc.CryptographyProvider
import io.github.serpro69.kfaker.provider.misc.RandomClassProvider
import io.github.serpro69.kfaker.provider.misc.RandomProvider
import io.github.serpro69.kfaker.provider.misc.StringProvider

/**
 * Provides access to fake data generators.
 *
 * Each category (generator) from this [Faker] is represented by a property that has the same name as the `.yml` file.
 *
 * @property random provides public access to the functions of [RandomService].
 * @property randomProvider provides additional functionality that is not covered by other data providers
 * such as [address], [name], [internet], and so on. See [RandomClassProvider] for more details.
 * @property string provides functionality to generate strings from expressions/templates
 * @property unique global provider for generation of unique values.
 */
@Suppress("unused")
class Faker @JvmOverloads constructor(config: FakerConfig = fakerConfig { }): AbstractFaker(config) {

    // misc providers
    val crypto: CryptographyProvider by lazy { CryptographyProvider(fakerService) }
    val random: RandomProvider by lazy { RandomProvider(fakerService) }
    // TODO rename to randomClass
    val randomProvider: RandomClassProvider by lazy { RandomClassProvider(config) }
    val string: StringProvider by lazy { StringProvider(fakerService) }

    val separator: Separator by lazy { Separator(fakerService) }
    val currencySymbol: CurrencySymbol by lazy { CurrencySymbol(fakerService) }

    // dictionary-based providers
    val address: Address by lazy { Address(fakerService) }
    val adjective: Adjective by lazy { Adjective(fakerService) }
    val airport: Airport by lazy { Airport(fakerService) }
    val ancient: Ancient by lazy { Ancient(fakerService) }
    val animal: Animal by lazy { Animal(fakerService) }
    val app: App by lazy { App(fakerService) }
    val appliance: Appliance by lazy { Appliance(fakerService) }
    val aquaTeenHungerForce: AquaTeenHungerForce by lazy { AquaTeenHungerForce(fakerService) }
    val archer: Archer by lazy { Archer(fakerService) }
    val artist: Artist by lazy { Artist(fakerService) }
    val australia: Australia by lazy { Australia(fakerService) }
    val avatar: Avatar by lazy { Avatar(fakerService) }
    val backToTheFuture: BackToTheFuture by lazy { BackToTheFuture(fakerService) }
    val bank: Bank by lazy { Bank(fakerService) }
    val barcode: Barcode by lazy { Barcode(fakerService) }
    val basketball: Basketball by lazy { Basketball(fakerService) }
    val beer: Beer by lazy { Beer(fakerService) }
    val bible: Bible by lazy { Bible(fakerService) }
    val bigBangTheory: BigBangTheory by lazy { BigBangTheory(fakerService) }
    val bird: Bird by lazy { Bird(fakerService) }
    val blood: Blood by lazy { Blood(fakerService) }
    val bojackHorseman: BojackHorseman by lazy { BojackHorseman(fakerService) }
    val bossaNova: BossaNova by lazy { BossaNova(fakerService) }
    val breakingBad: BreakingBad by lazy { BreakingBad(fakerService) }
    val brooklynNineNine: BrooklynNineNine by lazy { BrooklynNineNine(fakerService) }
    val buffy: Buffy by lazy { Buffy(fakerService) }
    val business: Business by lazy { Business(fakerService) }
    val camera: Camera by lazy { Camera(fakerService) }
    val cannabis: Cannabis by lazy { Cannabis(fakerService) }
    val cat: Cat by lazy { Cat(fakerService) }
    val chiquito: Chiquito by lazy { Chiquito(fakerService) }
    val chess: Chess by lazy { Chess(fakerService) }
    val chuckNorris: ChuckNorris by lazy { ChuckNorris(fakerService) }
    val code: Code by lazy { Code(fakerService) }
    val coffee: Coffee by lazy { Coffee(fakerService) }
    val coin: Coin by lazy { Coin(fakerService) }
    val color: Color by lazy { Color(fakerService) }
    val commerce: Commerce by lazy { Commerce(fakerService) }
    val community: Community by lazy { Community(fakerService) }
    val company: Company by lazy { Company(fakerService) }

    //    val compass: Compass by lazy {Compass(fakerService) }
    val computer: Computer by lazy { Computer(fakerService) }
    val conan: Conan by lazy { Conan(fakerService) }
    val construction: Construction by lazy { Construction(fakerService) }
    val cosmere: Cosmere by lazy { Cosmere(fakerService) }
    val cowboyBebop: CowboyBebop by lazy { CowboyBebop(fakerService) }
    val crossfit: Crossfit by lazy { Crossfit(fakerService) }
    val cryptoCoin: CryptoCoin by lazy { CryptoCoin(fakerService) }
    val currency: Currency by lazy { Currency(fakerService) }
    val dcComics: DcComics by lazy { DcComics(fakerService) }
    val demographic: Demographic by lazy { Demographic(fakerService) }
    val departed: Departed by lazy { Departed(fakerService) }
    val dessert: Dessert by lazy { Dessert(fakerService) }
    val device: Device by lazy { Device(fakerService) }
    val dog: Dog by lazy { Dog(fakerService) }
    val doraemon: Doraemon by lazy { Doraemon(fakerService) }
    val dragonBall: DragonBall by lazy { DragonBall(fakerService) }
    val drivingLicense: DrivingLicense by lazy { DrivingLicense(fakerService) }
    val drone: Drone by lazy { Drone(fakerService) }
    val drWho: DrWho by lazy { DrWho(fakerService) }
    val dumbAndDumber: DumbAndDumber by lazy { DumbAndDumber(fakerService) }
    val educator: Educator by lazy { Educator(fakerService) }
    val electricalComponents: ElectricalComponents by lazy { ElectricalComponents(fakerService) }
    val emotion: Emotion by lazy { Emotion(fakerService) }
    val eSport: ESport by lazy { ESport(fakerService) }
    val familyGuy: FamilyGuy by lazy { FamilyGuy(fakerService) }
    val file: File by lazy { File(fakerService) }
    val finalSpace: FinalSpace by lazy { FinalSpace(fakerService) }
    val finance: Finance by lazy { Finance(fakerService) }
    val fmaBrotherhood: FmaBrotherhood by lazy { FmaBrotherhood(fakerService) }
    val food: Food by lazy { Food(fakerService) }
    val football: Football by lazy { Football(fakerService) }
    val freshPriceOfBelAir: FreshPriceOfBelAir by lazy { FreshPriceOfBelAir(fakerService) }
    val friends: Friends by lazy { Friends(fakerService) }
    val funnyName: FunnyName by lazy { FunnyName(fakerService) }
    val futurama: Futurama by lazy { Futurama(fakerService) }
    val gameOfThrones: GameOfThrones by lazy { GameOfThrones(fakerService) }
    val gender: Gender by lazy { Gender(fakerService) }
    val ghostBusters: GhostBusters by lazy { GhostBusters(fakerService) }
    val gratefulDead: GratefulDead by lazy { GratefulDead(fakerService) }
    val greekPhilosophers: GreekPhilosophers by lazy { GreekPhilosophers(fakerService) }
    val hacker: Hacker by lazy { Hacker(fakerService) }
    val hackers: Hackers by lazy { Hackers(fakerService) }
    val harryPotter: HarryPotter by lazy { HarryPotter(fakerService) }
    val heyArnold: HeyArnold by lazy { HeyArnold(fakerService) }
    val hipster: Hipster by lazy { Hipster(fakerService) }
    val hitchhikersGuideToTheGalaxy: HitchhikersGuideToTheGalaxy by lazy { HitchhikersGuideToTheGalaxy(fakerService) }
    val hobbit: Hobbit by lazy { Hobbit(fakerService) }
    val hobby: Hobby by lazy { Hobby(fakerService) }
    val horse: Horse by lazy { Horse(fakerService) }
    val house: House by lazy { House(fakerService) }
    val howIMetYourMother: HowIMetYourMother by lazy { HowIMetYourMother(fakerService) }
    val howToTrainYourDragon: HowToTrainYourDragon by lazy { HowToTrainYourDragon(fakerService) }
    val idNumber: IdNumber by lazy { IdNumber(fakerService) }
    val industrySegments: IndustrySegments by lazy { IndustrySegments(fakerService) }
    val internet: Internet by lazy { Internet(fakerService, company, name) }

    //    val invoice: Invoice by lazy {Invoice(fakerService }
    val jackHandey: JackHandey by lazy { JackHandey(fakerService) }
    val job: Job by lazy { Job(fakerService) }
    val kamenRider: KamenRider by lazy { KamenRider(fakerService) }
    val kPop: KPop by lazy { KPop(fakerService) }
    val lebowski: Lebowski by lazy { Lebowski(fakerService) }
    val lordOfTheRings: LordOfTheRings by lazy { LordOfTheRings(fakerService) }
    val lorem: Lorem by lazy { Lorem(fakerService) }
    val markdown: Markdown by lazy { Markdown(fakerService) }
    val marketing: Marketing by lazy { Marketing(fakerService) }
    val measurement: Measurement by lazy { Measurement(fakerService) }
    val michaelScott: MichaelScott by lazy { MichaelScott(fakerService) }
    val military: Military by lazy { Military(fakerService) }
    val mitchHedberg: MitchHedberg by lazy { MitchHedberg(fakerService) }
    val money: Money by lazy { Money(fakerService) }
    val mountain: Mountain by lazy { Mountain(fakerService) }
    val mountaineering: Mountaineering by lazy { Mountaineering(fakerService) }
    val movie: Movie by lazy { Movie(fakerService) }
    val music: Music by lazy { Music(fakerService) }
    val name: Name by lazy { Name(fakerService) }
    val naruto: Naruto by lazy { Naruto(fakerService) }
    val nation: Nation by lazy { Nation(fakerService) }
    val natoPhoneticAlphabet: NatoPhoneticAlphabet by lazy { NatoPhoneticAlphabet(fakerService) }
    val newGirl: NewGirl by lazy { NewGirl(fakerService) }
    val onePiece: OnePiece by lazy { OnePiece(fakerService) }
    val opera: Opera by lazy { Opera(fakerService) }
    val parksAndRec: ParksAndRec by lazy { ParksAndRec(fakerService) }
    val pearlJam: PearlJam by lazy { PearlJam(fakerService) }
    val person: Person by lazy { Person(config.random) }
    val phish: Phish by lazy { Phish(fakerService) }
    val phoneNumber: PhoneNumber by lazy { PhoneNumber(fakerService) }
    val prince: Prince by lazy { Prince(fakerService) }
    val princessBride: PrincessBride by lazy { PrincessBride(fakerService) }
    val programmingLanguage: ProgrammingLanguage by lazy { ProgrammingLanguage(fakerService) }
    val quote: Quote by lazy { Quote(fakerService) }
    val rajnikanth: Rajnikanth by lazy { Rajnikanth(fakerService) }
    val relationship: Relationship by lazy { Relationship(fakerService) }
    val restaurant: Restaurant by lazy { Restaurant(fakerService) }
    val rickAndMorty: RickAndMorty by lazy { RickAndMorty(fakerService) }
    val rockBand: RockBand by lazy { RockBand(fakerService) }
    val room: Room by lazy { Room(fakerService) }
    val rupaul: Rupaul by lazy { Rupaul(fakerService) }
    val rush: Rush by lazy { Rush(fakerService) }
    val science: Science by lazy { Science(fakerService) }
    val seinfeld: Seinfeld by lazy { Seinfeld(fakerService) }
    val shakespeare: Shakespeare by lazy { Shakespeare(fakerService) }
    val show: Show by lazy { Show(fakerService) }
    val siliconValley: SiliconValley by lazy { SiliconValley(fakerService) }
    val simpsons: Simpsons by lazy { Simpsons(fakerService) }
    val slackEmoji: SlackEmoji by lazy { SlackEmoji(fakerService) }
    val smashingPumpkins: SmashingPumpkins by lazy { SmashingPumpkins(fakerService) }

    //    val source: Source by lazy {Source(fakerService }
    val southPark: SouthPark by lazy { SouthPark(fakerService) }
    val space: Space by lazy { Space(fakerService) }
    val spongebob: Spongebob by lazy { Spongebob(fakerService) }
    val sport: Sport by lazy { Sport(fakerService) }
    val stargate: Stargate by lazy { Stargate(fakerService) }
    val starTrek: StarTrek by lazy { StarTrek(fakerService) }
    val starWars: StarWars by lazy { StarWars(fakerService) }
    val strangerThings: StrangerThings by lazy { StrangerThings(fakerService) }
    val stripe: Stripe by lazy { Stripe(fakerService) }
    val studioGhibli: StudioGhibli by lazy { StudioGhibli(fakerService) }
    val subscription: Subscription by lazy { Subscription(fakerService) }
    val suits: Suits by lazy { Suits(fakerService) }
    val superhero: Superhero by lazy { Superhero(fakerService) }
    val supernatural: Supernatural by lazy { Supernatural(fakerService) }
    val swordArtOnline: SwordArtOnline by lazy { SwordArtOnline(fakerService) }
    val tarkov: Tarkov by lazy { Tarkov(fakerService) }
    val tea: Tea by lazy { Tea(fakerService) }
    val team: Team by lazy { Team(fakerService) }
    val theExpanse: TheExpanse by lazy { TheExpanse(fakerService) }
    val theITCrowd: TheITCrowd by lazy { TheITCrowd(fakerService) }
    val theOffice: TheOffice by lazy { TheOffice(fakerService) }
    val theRoom: TheRoom by lazy { TheRoom(fakerService) }
    val theThickOfIt: TheThickOfIt by lazy { TheThickOfIt(fakerService) }
    val tolkien: Tolkien by lazy { Tolkien(fakerService) }
    val trainStation: TrainStation by lazy { TrainStation(fakerService) }
    val tron: Tron by lazy { Tron(fakerService) }
    val twinPeaks: TwinPeaks by lazy { TwinPeaks(fakerService) }
    val umphreysMcgee: UmphreysMcgee by lazy { UmphreysMcgee(fakerService) }
    val university: University by lazy { University(fakerService) }
    val vehicle: Vehicle by lazy { Vehicle(fakerService) }
    val ventureBros: VentureBros by lazy { VentureBros(fakerService) }
    val verbs: Verbs by lazy { Verbs(fakerService) }
    val volleyball: Volleyball by lazy { Volleyball(fakerService) }
    val vForVendetta: VForVendetta by lazy { VForVendetta(fakerService) }
    val worldCup: WorldCup by lazy { WorldCup(fakerService) }
    val yoda: Yoda by lazy { Yoda(fakerService) }

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
 * Applies the [block] function to [Faker.Builder]
 * and returns as an instance of [Faker] from that builder.
 */
fun faker(block: Faker.Builder.() -> Unit): Faker = Faker.Builder().apply(block).build()
