@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package io.github.serpro69.kfaker

import io.github.serpro69.kfaker.provider.*
import io.github.serpro69.kfaker.provider.misc.CryptographyProvider
import io.github.serpro69.kfaker.provider.misc.RandomClassProvider
import io.github.serpro69.kfaker.provider.misc.StringProvider
import io.github.serpro69.kfaker.provider.unique.GlobalUniqueDataDataProvider

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
class Faker @JvmOverloads constructor(internal val config: FakerConfig = fakerConfig { }) {
    private val fakerService: FakerService = FakerService(this)

    val random by lazy { RandomService(config) }

    val unique by lazy { GlobalUniqueDataDataProvider() }

    // misc providers
    val crypto: CryptographyProvider by lazy { CryptographyProvider(fakerService) }
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
    val book: Book by lazy { Book(fakerService) }
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
    val clashOfClans: ClashOfClans by lazy { ClashOfClans(fakerService) }
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
    val control: Control by lazy { Control(fakerService) }
    val cosmere: Cosmere by lazy { Cosmere(fakerService) }
    val cowboyBebop: CowboyBebop by lazy { CowboyBebop(fakerService) }
    val crossfit: Crossfit by lazy { Crossfit(fakerService) }
    val cryptoCoin: CryptoCoin by lazy { CryptoCoin(fakerService) }
    val cultureSeries: CultureSeries by lazy { CultureSeries(fakerService) }
    val currency: Currency by lazy { Currency(fakerService) }
    val dcComics: DcComics by lazy { DcComics(fakerService) }
    val demographic: Demographic by lazy { Demographic(fakerService) }
    val departed: Departed by lazy { Departed(fakerService) }
    val dessert: Dessert by lazy { Dessert(fakerService) }
    val device: Device by lazy { Device(fakerService) }
    val dnd: DnD by lazy { DnD(fakerService) }
    val dog: Dog by lazy { Dog(fakerService) }
    val doraemon: Doraemon by lazy { Doraemon(fakerService) }
    val dota: Dota by lazy { Dota(fakerService) }
    val dragonBall: DragonBall by lazy { DragonBall(fakerService) }
    val drivingLicense: DrivingLicense by lazy { DrivingLicense(fakerService) }
    val drone: Drone by lazy { Drone(fakerService) }
    val drWho: DrWho by lazy { DrWho(fakerService) }
    val dumbAndDumber: DumbAndDumber by lazy { DumbAndDumber(fakerService) }
    val dune: Dune by lazy { Dune(fakerService) }
    val educator: Educator by lazy { Educator(fakerService) }
    val elderScrolls: ElderScrolls by lazy { ElderScrolls(fakerService) }
    val electricalComponents: ElectricalComponents by lazy { ElectricalComponents(fakerService) }
    val emotion: Emotion by lazy { Emotion(fakerService) }
    val eSport: ESport by lazy { ESport(fakerService) }
    val fallout: Fallout by lazy { Fallout(fakerService) }
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
    val game: Game by lazy { Game(fakerService) }
    val gameOfThrones: GameOfThrones by lazy { GameOfThrones(fakerService) }
    val gender: Gender by lazy { Gender(fakerService) }
    val ghostBusters: GhostBusters by lazy { GhostBusters(fakerService) }
    val gratefulDead: GratefulDead by lazy { GratefulDead(fakerService) }
    val greekPhilosophers: GreekPhilosophers by lazy { GreekPhilosophers(fakerService) }
    val hacker: Hacker by lazy { Hacker(fakerService) }
    val hackers: Hackers by lazy { Hackers(fakerService) }
    val halfLife: HalfLife by lazy { HalfLife(fakerService) }
    val harryPotter: HarryPotter by lazy { HarryPotter(fakerService) }
    val heroes: Heroes by lazy { Heroes(fakerService) }
    val heroesOfTheStorm: HeroesOfTheStorm by lazy { HeroesOfTheStorm(fakerService) }
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
    val internet: Internet by lazy { Internet(fakerService) }

    //    val invoice: Invoice by lazy {Invoice(fakerService }
    val jackHandey: JackHandey by lazy { JackHandey(fakerService) }
    val job: Job by lazy { Job(fakerService) }
    val kamenRider: KamenRider by lazy { KamenRider(fakerService) }
    val kPop: KPop by lazy { KPop(fakerService) }
    val leagueOfLegends: LeagueOfLegends by lazy { LeagueOfLegends(fakerService) }
    val lebowski: Lebowski by lazy { Lebowski(fakerService) }
    val lordOfTheRings: LordOfTheRings by lazy { LordOfTheRings(fakerService) }
    val lorem: Lorem by lazy { Lorem(fakerService) }
    val lovecraft: Lovecraft by lazy { Lovecraft(fakerService) }
    val markdown: Markdown by lazy { Markdown(fakerService) }
    val marketing: Marketing by lazy { Marketing(fakerService) }
    val measurement: Measurement by lazy { Measurement(fakerService) }
    val michaelScott: MichaelScott by lazy { MichaelScott(fakerService) }
    val military: Military by lazy { Military(fakerService) }
    val minecraft: Minecraft by lazy { Minecraft(fakerService) }
    val money: Money by lazy { Money(fakerService) }
    val mountain: Mountain by lazy { Mountain(fakerService) }
    val mountaineering: Mountaineering by lazy { Mountaineering(fakerService) }
    val movie: Movie by lazy { Movie(fakerService) }
    val music: Music by lazy { Music(fakerService) }
    val myst: Myst by lazy { Myst(fakerService) }
    val name: Name by lazy { Name(fakerService) }
    val naruto: Naruto by lazy { Naruto(fakerService) }
    val nation: Nation by lazy { Nation(fakerService) }
    val natoPhoneticAlphabet: NatoPhoneticAlphabet by lazy { NatoPhoneticAlphabet(fakerService) }
    val newGirl: NewGirl by lazy { NewGirl(fakerService) }
    val onePiece: OnePiece by lazy { OnePiece(fakerService) }
    val opera: Opera by lazy { Opera(fakerService) }
    val overwatch: Overwatch by lazy { Overwatch(fakerService) }
    val parksAndRec: ParksAndRec by lazy { ParksAndRec(fakerService) }
    val pearlJam: PearlJam by lazy { PearlJam(fakerService) }
    val person: Person by lazy { Person(config.random) }
    val phish: Phish by lazy { Phish(fakerService) }
    val phoneNumber: PhoneNumber by lazy { PhoneNumber(fakerService) }
    val pokemon: Pokemon by lazy { Pokemon(fakerService) }
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
    val sonicTheHedgehog: SonicTheHedgehog by lazy { SonicTheHedgehog(fakerService) }

    //    val source: Source by lazy {Source(fakerService }
    val southPark: SouthPark by lazy { SouthPark(fakerService) }
    val space: Space by lazy { Space(fakerService) }
    val spongebob: Spongebob by lazy { Spongebob(fakerService) }
    val sport: Sport by lazy { Sport(fakerService) }
    val stargate: Stargate by lazy { Stargate(fakerService) }
    val starTrek: StarTrek by lazy { StarTrek(fakerService) }
    val starWars: StarWars by lazy { StarWars(fakerService) }
    val strangerThings: StrangerThings by lazy { StrangerThings(fakerService) }
    val streetFighter: StreetFighter by lazy { StreetFighter(fakerService) }
    val stripe: Stripe by lazy { Stripe(fakerService) }
    val studioGhibli: StudioGhibli by lazy { StudioGhibli(fakerService) }
    val subscription: Subscription by lazy { Subscription(fakerService) }
    val suits: Suits by lazy { Suits(fakerService) }
    val superMario: SuperMario by lazy { SuperMario(fakerService) }
    val superhero: Superhero by lazy { Superhero(fakerService) }
    val supernatural: Supernatural by lazy { Supernatural(fakerService) }
    val superSmashBros: SuperSmashBros by lazy { SuperSmashBros(fakerService) }
    val swordArtOnline: SwordArtOnline by lazy { SwordArtOnline(fakerService) }
    val tarkov: Tarkov by lazy { Tarkov(fakerService) }
    val tea: Tea by lazy { Tea(fakerService) }
    val team: Team by lazy { Team(fakerService) }
    val theExpanse: TheExpanse by lazy { TheExpanse(fakerService) }
    val theITCrowd: TheITCrowd by lazy { TheITCrowd(fakerService) }
    val theKingkillerChronicle: TheKingkillerChronicle by lazy { TheKingkillerChronicle(fakerService) }
    val theOffice: TheOffice by lazy { TheOffice(fakerService) }
    val theThickOfIt: TheThickOfIt by lazy { TheThickOfIt(fakerService) }
    val tolkien: Tolkien by lazy { Tolkien(fakerService) }
    val touhou: Touhou by lazy { Touhou(fakerService) }
    val tron: Tron by lazy { Tron(fakerService) }
    val twinPeaks: TwinPeaks by lazy { TwinPeaks(fakerService) }
    val umphreysMcgee: UmphreysMcgee by lazy { UmphreysMcgee(fakerService) }
    val university: University by lazy { University(fakerService) }
    val vehicle: Vehicle by lazy { Vehicle(fakerService) }
    val ventureBros: VentureBros by lazy { VentureBros(fakerService) }
    val verbs: Verbs by lazy { Verbs(fakerService) }
    val volleyball: Volleyball by lazy { Volleyball(fakerService) }
    val warhammerFantasy: WarhammerFantasy by lazy { WarhammerFantasy(fakerService) }
    val vForVendetta: VForVendetta by lazy { VForVendetta(fakerService) }
    val witcher: Witcher by lazy { Witcher(fakerService) }
    val worldCup: WorldCup by lazy { WorldCup(fakerService) }
    val worldOfWarcraft: WorldOfWarcraft by lazy { WorldOfWarcraft(fakerService) }
    val yoda: Yoda by lazy { Yoda(fakerService) }
    val zelda: Zelda by lazy { Zelda(fakerService) }

    @FakerDsl
    /**
     * DSL builder for creating instances of [Faker]
     */
    class Builder internal constructor() {
        /**
         * @property config faker configuration for the [Faker] instance
         * which will be created with this [Faker.Builder].
         */
        private var config: FakerConfig = io.github.serpro69.kfaker.fakerConfig { }

        /**
         * Sets [config] configuration for this [Faker.Builder]
         * using the results of the [block] function.
         *
         * This [config] will then be used when an instance of [Faker] is created using this [Faker.Builder]
         */
        fun fakerConfig(block: ConfigBuilder) {
            config = io.github.serpro69.kfaker.fakerConfig(block)
        }

        /**
         * Builds an instance of [Faker] with this [config].
         */
        internal fun build(): Faker = Faker(config)
    }
}

/**
 * Applies the the [block] function to [Faker.Builder]
 * and returns as an instance of [Faker] from that builder.
 */
fun faker(block: Faker.Builder.() -> Unit): Faker = Faker.Builder().apply(block).build()
