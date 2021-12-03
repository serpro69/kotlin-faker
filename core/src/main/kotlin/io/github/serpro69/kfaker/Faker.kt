@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package io.github.serpro69.kfaker

import io.github.serpro69.kfaker.provider.*
import io.github.serpro69.kfaker.provider.unique.GlobalUniqueDataDataProvider

/**
 * Provides access to fake data generators.
 *
 * Each category (generator) from this [Faker] is represented by a property that has the same name as the `.yml` file.
 *
 * @property random provides public access to functions of [RandomService].
 * @property randomProvider provides additional functionality that is not covered by other data providers
 * such as [address], [name], [internet], and so on. See [RandomProvider] for more details.
 * @property unique global provider for generation of unique values.
 */
class Faker @JvmOverloads constructor(internal val config: FakerConfig = fakerConfig { }) {
    private val fakerService: FakerService = FakerService(this, config.locale)

    val random = RandomService(config)

    val unique = GlobalUniqueDataDataProvider()

    val randomProvider: RandomProvider = RandomProvider(config)

    val separator: Separator = Separator(fakerService)
    val currencySymbol: CurrencySymbol = CurrencySymbol(fakerService)

    val address: Address = Address(fakerService)
    val ancient: Ancient = Ancient(fakerService)
    val animal: Animal = Animal(fakerService)
    val app: App = App(fakerService)
    val appliance: Appliance = Appliance(fakerService)
    val aquaTeenHungerForce: AquaTeenHungerForce = AquaTeenHungerForce(fakerService)
    val artist: Artist = Artist(fakerService)
    val backToTheFuture: BackToTheFuture = BackToTheFuture(fakerService)
    val bank: Bank = Bank(fakerService)
    val barcode: Barcode = Barcode(fakerService)
    val basketball: Basketball = Basketball(fakerService)
    val beer: Beer = Beer(fakerService)
    val bigBangTheory: BigBangTheory = BigBangTheory(fakerService)
    val blood: Blood = Blood(fakerService)
    val bojackHorseman: BojackHorseman = BojackHorseman(fakerService)
    val book: Book = Book(fakerService)
    val bossaNova: BossaNova = BossaNova(fakerService)
    val breakingBad: BreakingBad = BreakingBad(fakerService)
    val buffy: Buffy = Buffy(fakerService)
    val business: Business = Business(fakerService)
    val cannabis: Cannabis = Cannabis(fakerService)
    val cat: Cat = Cat(fakerService)
    val chiquito: Chiquito = Chiquito(fakerService)
    val chuckNorris: ChuckNorris = ChuckNorris(fakerService)
    val code: Code = Code(fakerService)
    val coffee: Coffee = Coffee(fakerService)
    val coin: Coin = Coin(fakerService)
    val color: Color = Color(fakerService)
    val commerce: Commerce = Commerce(fakerService)
    val community: Community = Community(fakerService)
    val company: Company = Company(fakerService)
    //    val compass: Compass = Compass(fakerService)
    val computer: Computer = Computer(fakerService)
    val construction: Construction = Construction(fakerService)
    val control: Control = Control(fakerService)
    val cosmere: Cosmere = Cosmere(fakerService)
    val crossfit: Crossfit = Crossfit(fakerService)
    val cryptoCoin: CryptoCoin = CryptoCoin(fakerService)
    val cultureSeries: CultureSeries = CultureSeries(fakerService)
    val currency: Currency = Currency(fakerService)
    val dcComics: DcComics = DcComics(fakerService)
    val demographic: Demographic = Demographic(fakerService)
    val departed: Departed = Departed(fakerService)
    val dessert: Dessert = Dessert(fakerService)
    val device: Device = Device(fakerService)
    val dnd: DnD = DnD(fakerService)
    val dog: Dog = Dog(fakerService)
    val dota: Dota = Dota(fakerService)
    val dragonBall: DragonBall = DragonBall(fakerService)
    val drivingLicense: DrivingLicense = DrivingLicense(fakerService)
    val drone: Drone = Drone(fakerService)
    val drWho: DrWho = DrWho(fakerService)
    val dumbAndDumber: DumbAndDumber = DumbAndDumber(fakerService)
    val dune: Dune = Dune(fakerService)
    val educator: Educator = Educator(fakerService)
    val elderScrolls: ElderScrolls = ElderScrolls(fakerService)
    val electricalComponents: ElectricalComponents = ElectricalComponents(fakerService)
    val eSport: ESport = ESport(fakerService)
    val fallout: Fallout = Fallout(fakerService)
    val familyGuy: FamilyGuy = FamilyGuy(fakerService)
    val file: File = File(fakerService)
//    val finance: Finance = Finance(fakerService
    val food: Food = Food(fakerService)
    val football: Football = Football(fakerService)
    val freshPriceOfBelAir: FreshPriceOfBelAir = FreshPriceOfBelAir(fakerService)
    val friends: Friends = Friends(fakerService)
    val funnyName: FunnyName = FunnyName(fakerService)
    val futurama: Futurama = Futurama(fakerService)
    val game: Game = Game(fakerService)
    val gameOfThrones: GameOfThrones = GameOfThrones(fakerService)
    val gender: Gender = Gender(fakerService)
    val ghostBusters: GhostBusters = GhostBusters(fakerService)
    val gratefulDead: GratefulDead = GratefulDead(fakerService)
    val greekPhilosophers: GreekPhilosophers = GreekPhilosophers(fakerService)
    val hacker: Hacker = Hacker(fakerService)
    val halfLife: HalfLife = HalfLife(fakerService)
    val harryPotter: HarryPotter = HarryPotter(fakerService)
    val heroes: Heroes = Heroes(fakerService)
    val heroesOfTheStorm: HeroesOfTheStorm = HeroesOfTheStorm(fakerService)
    val heyArnold: HeyArnold = HeyArnold(fakerService)
    val hipster: Hipster = Hipster(fakerService)
    val hitchhikersGuideToTheGalaxy: HitchhikersGuideToTheGalaxy = HitchhikersGuideToTheGalaxy(fakerService)
    val hobbit: Hobbit = Hobbit(fakerService)
    val horse: Horse = Horse(fakerService)
    val house: House = House(fakerService)
    val howIMetYourMother: HowIMetYourMother = HowIMetYourMother(fakerService)
    val idNumber: IdNumber = IdNumber(fakerService)
    val industrySegments: IndustrySegments = IndustrySegments(fakerService)
    val internet: Internet = Internet(fakerService)
    //    val invoice: Invoice = Invoice(fakerService
    val job: Job = Job(fakerService)
    val kPop: KPop = KPop(fakerService)
    val leagueOfLegends: LeagueOfLegends = LeagueOfLegends(fakerService)
    val lebowski: Lebowski = Lebowski(fakerService)
    val lordOfTheRings: LordOfTheRings = LordOfTheRings(fakerService)
    val lorem: Lorem = Lorem(fakerService)
    val lovecraft: Lovecraft = Lovecraft(fakerService)
    val markdown: Markdown = Markdown(fakerService)
    val marketing: Marketing = Marketing(fakerService)
    val measurement: Measurement = Measurement(fakerService)
    val michaelScott: MichaelScott = MichaelScott(fakerService)
    val military: Military = Military(fakerService)
    val minecraft: Minecraft = Minecraft(fakerService)
    val money: Money = Money(fakerService)
    val movie: Movie = Movie(fakerService)
    val music: Music = Music(fakerService)
    val myst: Myst = Myst(fakerService)
    val name: Name = Name(fakerService)
    val nation: Nation = Nation(fakerService)
    val natoPhoneticAlphabet: NatoPhoneticAlphabet = NatoPhoneticAlphabet(fakerService)
    val newGirl: NewGirl = NewGirl(fakerService)
    val onePiece: OnePiece = OnePiece(fakerService)
    val opera: Opera = Opera(fakerService)
    val overwatch: Overwatch = Overwatch(fakerService)
    val parksAndRec: ParksAndRec = ParksAndRec(fakerService)
    val pearlJam: PearlJam = PearlJam(fakerService)
    val person: Person = Person(config.random)
    val phish: Phish = Phish(fakerService)
    val phoneNumber: PhoneNumber = PhoneNumber(fakerService)
    val pokemon: Pokemon = Pokemon(fakerService)
    val prince: Prince = Prince(fakerService)
    val princessBride: PrincessBride = PrincessBride(fakerService)
    val programmingLanguage: ProgrammingLanguage = ProgrammingLanguage(fakerService)
    val quote: Quote = Quote(fakerService)
    val rajnikanth: Rajnikanth = Rajnikanth(fakerService)
    val relationship: Relationship = Relationship(fakerService)
    val restaurant: Restaurant = Restaurant(fakerService)
    val rickAndMorty: RickAndMorty = RickAndMorty(fakerService)
    val rockBand: RockBand = RockBand(fakerService)
    val rupaul: Rupaul = Rupaul(fakerService)
    val rush: Rush = Rush(fakerService)
    val science: Science = Science(fakerService)
    val seinfeld: Seinfeld = Seinfeld(fakerService)
    val shakespeare: Shakespeare = Shakespeare(fakerService)
    val show: Show = Show(fakerService)
    val siliconValley: SiliconValley = SiliconValley(fakerService)
    val simpsons: Simpsons = Simpsons(fakerService)
    val slackEmoji: SlackEmoji = SlackEmoji(fakerService)
    val sonicTheHedgehog: SonicTheHedgehog = SonicTheHedgehog(fakerService)
    //    val source: Source = Source(fakerService
    val southPark: SouthPark = SouthPark(fakerService)
    val space: Space = Space(fakerService)
    val stargate: Stargate = Stargate(fakerService)
    val starTrek: StarTrek = StarTrek(fakerService)
    val starWars: StarWars = StarWars(fakerService)
    val strangerThings: StrangerThings = StrangerThings(fakerService)
    val streetFighter: StreetFighter = StreetFighter(fakerService)
    val stripe: Stripe = Stripe(fakerService)
    val subscription: Subscription = Subscription(fakerService)
    val suits: Suits = Suits(fakerService)
    val superhero: Superhero = Superhero(fakerService)
    val superSmashBros: SuperSmashBros = SuperSmashBros(fakerService)
    val swordArtOnline: SwordArtOnline = SwordArtOnline(fakerService)
    val team: Team = Team(fakerService)
    val theExpanse: TheExpanse = TheExpanse(fakerService)
    val theITCrowd: TheITCrowd = TheITCrowd(fakerService)
    val theThickOfIt: TheThickOfIt = TheThickOfIt(fakerService)
    val twinPeaks: TwinPeaks = TwinPeaks(fakerService)
    val umphreysMcgee: UmphreysMcgee = UmphreysMcgee(fakerService)
    val university: University = University(fakerService)
    val vehicle: Vehicle = Vehicle(fakerService)
    val ventureBros: VentureBros = VentureBros(fakerService)
    val verbs: Verbs = Verbs(fakerService)
    val warhammerFantasy: WarhammerFantasy = WarhammerFantasy(fakerService)
    val vForVendetta: VForVendetta = VForVendetta(fakerService)
    val witcher: Witcher = Witcher(fakerService)
    val worldCup: WorldCup = WorldCup(fakerService)
    val worldOfWarcraft: WorldOfWarcraft = WorldOfWarcraft(fakerService)
    val yoda: Yoda = Yoda(fakerService)
    val zelda: Zelda = Zelda(fakerService)

    @FakerDsl
    /**
     * DSL builder for creating instances of [Faker]
     */
    class Builder internal constructor(){
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
