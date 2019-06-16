package io.github.serpro69.kfaker

import io.github.serpro69.kfaker.provider.*
import io.github.serpro69.kfaker.provider.Currency
import java.util.*

/**
 * Object provides functionality to generate fake data.
 *
 * Each category from this [Faker] is represented by a property that has the same name as the `.yml` file.
 */
object Faker {
    private lateinit var fakerService: FakerService

    lateinit var separator: Separator private set
    lateinit var address: Address private set
    lateinit var ancient: Ancient private set
    lateinit var animal: Animal private set
    lateinit var app: App private set
    lateinit var appliance: Appliance private set
    lateinit var aquaTeenHungerForce: AquaTeenHungerForce private set
    lateinit var artist: Artist private set
    lateinit var backToTheFuture: BackToTheFuture private set
    lateinit var bank: Bank private set
    lateinit var basketball: Basketball private set
    lateinit var beer: Beer private set
    lateinit var bojackHoreseman: BojackHoreseman private set
    lateinit var book: Book private set
    lateinit var bossaNova: BossaNova private set
    lateinit var breakingBad: BreakingBad private set
    lateinit var buffy: Buffy private set
    lateinit var business: Business private set
    lateinit var cannabis: Cannabis private set
    lateinit var cat: Cat private set
    lateinit var chuckNorris: ChuckNorris private set
    lateinit var code: Code private set
    lateinit var coffee: Coffee private set
    lateinit var coin: Coin private set
    lateinit var color: Color private set
    lateinit var commerce: Commerce private set
    lateinit var community: Community private set
    lateinit var company: Company private set
//    lateinit var compass: Compass private set
    lateinit var construction: Construction private set
    lateinit var cosmere: Cosmere private set
    lateinit var cryptoCoin: CryptoCoin private set
    lateinit var cultureSeries: CultureSeries private set
    lateinit var currency: Currency private set
    lateinit var dcComics: DcComics private set
    lateinit var demographic: Demographic private set
    lateinit var dessert: Dessert private set
    lateinit var device: Device private set
    lateinit var dog: Dog private set
    lateinit var dota: Dota private set
    lateinit var dragonBall: DragonBall private set
    lateinit var drWho: DrWho private set
    lateinit var dumbAndDumber: DumbAndDumber private set
    lateinit var dune: Dune private set
    lateinit var educator: Educator private set
    lateinit var elderScrolls: ElderScrolls private set
    lateinit var electricalComponents: ElectricalComponents private set
    lateinit var eSport: ESport private set
    lateinit var fallout: Fallout private set
    lateinit var familyGuy: FamilyGuy private set
    lateinit var file: File private set
//    lateinit var finance: Finance private set
    lateinit var food: Food private set
    lateinit var football: Football private set
    lateinit var freshPriceOfBelAir: FreshPriceOfBelAir private set
    lateinit var friends: Friends private set
    lateinit var funnyName: FunnyName private set
    lateinit var gameOfThrones: GameOfThrones private set
    lateinit var gender: Gender private set
    lateinit var ghostBusters: GhostBusters private set
    lateinit var gratefulDead: GratefulDead private set
    lateinit var greekPhilosophers: GreekPhilosophers private set
    lateinit var hacker: Hacker private set
    lateinit var halfLife: HalfLife private set
    lateinit var harryPotter: HarryPotter private set
    lateinit var heroes: Heroes private set
    lateinit var heroesOfTheStorm: HeroesOfTheStorm private set
    lateinit var heyArnold: HeyArnold private set
    lateinit var hipster: Hipster private set
    lateinit var hitchhikersGuideToTheGalaxy: HitchhikersGuideToTheGalaxy private set
    lateinit var hobbit: Hobbit private set
    lateinit var house: House private set
    lateinit var howIMetYourMother: HowIMetYourMother private set
    lateinit var idNumber: IdNumber private set
    lateinit var industrySegments: IndustrySegments private set
    lateinit var internet: Internet private set
//    lateinit var invoice: Invoice private set
    lateinit var job: Job private set
    lateinit var kPop: KPop private set
    lateinit var leagueOfLegends: LeagueOfLegends private set
    lateinit var lebowski: Lebowski private set
    lateinit var lordOfTheRings: LordOfTheRings private set
    lateinit var lorem: Lorem private set
    lateinit var lovecraft: Lovecraft private set
    lateinit var markdown: Markdown private set
    lateinit var marketing: Marketing private set
    lateinit var measurement: Measurement private set
    lateinit var michaelScott: MichaelScott private set
    lateinit var military: Military private set
    lateinit var movie: Movie private set
    lateinit var music: Music private set
    lateinit var myst: Myst private set
    lateinit var name: Name private set
    lateinit var nation: Nation private set
    lateinit var natoPhoneticAlphabet: NatoPhoneticAlphabet private set
    lateinit var newGirl: NewGirl private set
    lateinit var onePiece: OnePiece private set
    lateinit var overwatch: Overwatch private set
    lateinit var parksAndRec: ParksAndRec private set
    lateinit var phish: Phish private set
    lateinit var phoneNumber: PhoneNumber private set
    lateinit var pokemon: Pokemon private set
    lateinit var princessBride: PrincessBride private set
    lateinit var programmingLanguage: ProgrammingLanguage private set
    lateinit var quote: Quote private set
    lateinit var relationship: Relationship private set
    lateinit var restaurant: Restaurant private set
    lateinit var rickAndMorty: RickAndMorty private set
    lateinit var rockBand: RockBand private set
    lateinit var rupaul: Rupaul private set
    lateinit var science: Science private set
    lateinit var seinfeld: Seinfeld private set
    lateinit var shakespeare: Shakespeare private set
    lateinit var siliconValley: SiliconValley private set
    lateinit var simpsons: Simpsons private set
    lateinit var slackEmoji: SlackEmoji private set
    lateinit var sonicTheHedgehog: SonicTheHedgehog private set
//    lateinit var source: Source private set
    lateinit var southPark: SouthPark private set
    lateinit var space: Space private set
    lateinit var stargate: Stargate private set
    lateinit var starTrek: StarTrek private set
    lateinit var starWars: StarWars private set
    lateinit var strangerThings: StrangerThings private set
    lateinit var stripe: Stripe private set
    lateinit var subscription: Subscription private set
    lateinit var superhero: Superhero private set
    lateinit var superSmashBros: SuperSmashBros private set
    lateinit var swordArtOnline: SwordArtOnline private set
    lateinit var team: Team private set
    lateinit var theExpanse: TheExpanse private set
    lateinit var theITCrowd: TheITCrowd private set
    lateinit var theThickOfIt: TheThickOfIt private set
    lateinit var twinPeaks: TwinPeaks private set
    lateinit var umphreysMcgee: UmphreysMcgee private set
    lateinit var university: University private set
    lateinit var vehicle: Vehicle private set
    lateinit var ventureBros: VentureBros private set
    lateinit var verbs: Verbs private set
    lateinit var vForVendetta: VForVendetta private set
    lateinit var witcher: Witcher private set
    lateinit var worldCup: WorldCup private set
    lateinit var worldOfWarcraft: WorldOfWarcraft private set
    lateinit var yoda: Yoda private set
    lateinit var zelda: Zelda private set

    val randomProvider = RandomProvider()

    @JvmStatic
    fun init(locale: Locale) = init(locale.toLanguageTag())

    @JvmStatic
    @JvmOverloads
    fun init(locale: String = "en"): Faker {
        fakerService = FakerService(locale)

        separator = Separator(fakerService)
        address = Address(fakerService)
        ancient = Ancient(fakerService)
        animal = Animal(fakerService)
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
//        compass = Compass(fakerService)
        construction = Construction(fakerService)
        cosmere = Cosmere(fakerService)
        cryptoCoin = CryptoCoin(fakerService)
        cultureSeries = CultureSeries(fakerService)
        currency = Currency(fakerService)
        dcComics = DcComics(fakerService)
        demographic = Demographic(fakerService)
        dessert = Dessert(fakerService)
        device = Device(fakerService)
        dog = Dog(fakerService)
        dota = Dota(fakerService)
        dragonBall = DragonBall(fakerService)
        drWho = DrWho(fakerService)
        dumbAndDumber = DumbAndDumber(fakerService)
        dune = Dune(fakerService)
        educator = Educator(fakerService)
        elderScrolls = ElderScrolls(fakerService)
        electricalComponents = ElectricalComponents(fakerService)
        eSport = ESport(fakerService)
//        fakeDataProvider = FakeDataProvider(fakerService)
        fallout = Fallout(fakerService)
        familyGuy = FamilyGuy(fakerService)
        file = File(fakerService)
//        finance = Finance(fakerService)
        food = Food(fakerService)
        football = Football(fakerService)
        freshPriceOfBelAir = FreshPriceOfBelAir(fakerService)
        friends = Friends(fakerService)
        funnyName = FunnyName(fakerService)
        gameOfThrones = GameOfThrones(fakerService)
        gender = Gender(fakerService)
        ghostBusters = GhostBusters(fakerService)
        gratefulDead = GratefulDead(fakerService)
        greekPhilosophers = GreekPhilosophers(fakerService)
        hacker = Hacker(fakerService)
        halfLife = HalfLife(fakerService)
        harryPotter = HarryPotter(fakerService)
        heroes = Heroes(fakerService)
        heroesOfTheStorm = HeroesOfTheStorm(fakerService)
        heyArnold = HeyArnold(fakerService)
        hipster = Hipster(fakerService)
        hitchhikersGuideToTheGalaxy = HitchhikersGuideToTheGalaxy(fakerService)
        hobbit = Hobbit(fakerService)
        house = House(fakerService)
        howIMetYourMother = HowIMetYourMother(fakerService)
        idNumber = IdNumber(fakerService)
        industrySegments = IndustrySegments(fakerService)
        internet = Internet(fakerService)
//        invoice = Invoice(fakerService)
        job = Job(fakerService)
        kPop = KPop(fakerService)
        leagueOfLegends = LeagueOfLegends(fakerService)
        lebowski = Lebowski(fakerService)
        lordOfTheRings = LordOfTheRings(fakerService)
        lorem = Lorem(fakerService)
        lovecraft = Lovecraft(fakerService)
        markdown = Markdown(fakerService)
        marketing = Marketing(fakerService)
        measurement = Measurement(fakerService)
        michaelScott = MichaelScott(fakerService)
        military = Military(fakerService)
        movie = Movie(fakerService)
        music = Music(fakerService)
        myst = Myst(fakerService)
        name = Name(fakerService)
        nation = Nation(fakerService)
        natoPhoneticAlphabet = NatoPhoneticAlphabet(fakerService)
        newGirl = NewGirl(fakerService)
        onePiece = OnePiece(fakerService)
        overwatch = Overwatch(fakerService)
        parksAndRec = ParksAndRec(fakerService)
        phish = Phish(fakerService)
        phoneNumber = PhoneNumber(fakerService)
        pokemon = Pokemon(fakerService)
        princessBride = PrincessBride(fakerService)
        programmingLanguage = ProgrammingLanguage(fakerService)
        quote = Quote(fakerService)
        relationship = Relationship(fakerService)
        restaurant = Restaurant(fakerService)
        rickAndMorty = RickAndMorty(fakerService)
        rockBand = RockBand(fakerService)
        rupaul = Rupaul(fakerService)
        science = Science(fakerService)
        seinfeld = Seinfeld(fakerService)
        shakespeare = Shakespeare(fakerService)
        siliconValley = SiliconValley(fakerService)
        simpsons = Simpsons(fakerService)
        slackEmoji = SlackEmoji(fakerService)
        sonicTheHedgehog = SonicTheHedgehog(fakerService)
//        source = Source(fakerService)
        southPark = SouthPark(fakerService)
        space = Space(fakerService)
        stargate = Stargate(fakerService)
        starTrek = StarTrek(fakerService)
        starWars = StarWars(fakerService)
        strangerThings = StrangerThings(fakerService)
        stripe = Stripe(fakerService)
        subscription = Subscription(fakerService)
        superhero = Superhero(fakerService)
        superSmashBros = SuperSmashBros(fakerService)
        swordArtOnline = SwordArtOnline(fakerService)
        team = Team(fakerService)
        theExpanse = TheExpanse(fakerService)
        theITCrowd = TheITCrowd(fakerService)
        theThickOfIt = TheThickOfIt(fakerService)
        twinPeaks = TwinPeaks(fakerService)
        umphreysMcgee = UmphreysMcgee(fakerService)
        university = University(fakerService)
        vehicle = Vehicle(fakerService)
        ventureBros = VentureBros(fakerService)
        verbs = Verbs(fakerService)
        vForVendetta = VForVendetta(fakerService)
        witcher = Witcher(fakerService)
        worldCup = WorldCup(fakerService)
        worldOfWarcraft = WorldOfWarcraft(fakerService)
        yoda = Yoda(fakerService)
        zelda = Zelda(fakerService)

        return this
    }
}
