package io.github.serpro69.kfaker

import io.github.serpro69.kfaker.provider.*
import io.github.serpro69.kfaker.provider.Currency
import java.util.*

object Faker {
    private lateinit var fakerService: FakerService

    lateinit var address: Address
    lateinit var ancient: Ancient
    lateinit var animal: Animal
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
//    lateinit var compass: Compass
    lateinit var construction: Construction
    lateinit var cosmere: Cosmere
    lateinit var cryptoCoin: CryptoCoin
    lateinit var cultureSeries: CultureSeries
    lateinit var currency: Currency
    lateinit var dcComics: DcComics
    lateinit var demographic: Demographic
    lateinit var dessert: Dessert
    lateinit var device: Device
    lateinit var dog: Dog
//    lateinit var dota: Dota
    lateinit var dragonBall: DragonBall
    lateinit var drWho: DrWho
    lateinit var dumbAndDumber: DumbAndDumber
    lateinit var dune: Dune
    lateinit var educator: Educator
    lateinit var elderScrolls: ElderScrolls
    lateinit var electricalComponents: ElectricalComponents
    lateinit var eSport: ESport
    lateinit var fallout: Fallout
    lateinit var familyGuy: FamilyGuy
    lateinit var file: File
//    lateinit var finance: Finance
    lateinit var food: Food
    lateinit var football: Football
    lateinit var freshPriceOfBelAir: FreshPriceOfBelAir
    lateinit var friends: Friends
    lateinit var funnyName: FunnyName
    lateinit var gameOfThrones: GameOfThrones
    lateinit var gender: Gender
    lateinit var ghostBusters: GhostBusters
    lateinit var gratefulDead: GratefulDead
    lateinit var greekPhilosophers: GreekPhilosophers
    lateinit var hacker: Hacker
    lateinit var halfLife: HalfLife
    lateinit var harryPotter: HarryPotter
    lateinit var heroes: Heroes
    lateinit var heroesOfTheStorm: HeroesOfTheStorm
    lateinit var heyArnold: HeyArnold
    lateinit var hipster: Hipster
    lateinit var hitchhikersGuideToTheGalaxy: HitchhikersGuideToTheGalaxy
    lateinit var hobbit: Hobbit
    lateinit var house: House
    lateinit var howIMetYourMother: HowIMetYourMother
    lateinit var idNumber: IdNumber
    lateinit var industrySegments: IndustrySegments
    lateinit var internet: Internet
    lateinit var invoice: Invoice
    lateinit var job: Job
    lateinit var kPop: KPop
    lateinit var leagueOfLegends: LeagueOfLegends
    lateinit var lebowski: Lebowski
    lateinit var lordOfTheRings: LordOfTheRings
    lateinit var lorem: Lorem
    lateinit var lovecraft: Lovecraft
    lateinit var markdown: Markdown
    lateinit var marketing: Marketing
    lateinit var measurement: Measurement
    lateinit var michaelScott: MichaelScott
    lateinit var military: Military
    lateinit var movie: Movie
    lateinit var music: Music
    lateinit var myst: Myst
    lateinit var name: Name
    lateinit var nation: Nation
    lateinit var natoPhoneticAlphabet: NatoPhoneticAlphabet
    lateinit var newGirl: NewGirl
    lateinit var onePiece: OnePiece
    lateinit var overwatch: Overwatch
    lateinit var parksAndRec: ParksAndRec
    lateinit var phish: Phish
    lateinit var phoneNumber: PhoneNumber
    lateinit var pokemon: Pokemon
    lateinit var princessBride: PrincessBride
    lateinit var programmingLanguage: ProgrammingLanguage
    lateinit var quote: Quote
    lateinit var relationship: Relationship
    lateinit var restaurant: Restaurant
    lateinit var rickAndMorty: RickAndMorty
    lateinit var rockBand: RockBand
    lateinit var rupaul: Rupaul
    lateinit var science: Science
    lateinit var seinfeld: Seinfeld
    lateinit var shakespeare: Shakespeare
    lateinit var siliconValley: SiliconValley
    lateinit var simpsons: Simpsons
    lateinit var slackEmoji: SlackEmoji
    lateinit var sonicTheHedgehog: SonicTheHedgehog
//    lateinit var source: Source
    lateinit var southPark: SouthPark
    lateinit var space: Space
    lateinit var stargate: Stargate
    lateinit var starTrek: StarTrek
    lateinit var starWars: StarWars
    lateinit var strangerThings: StrangerThings
    lateinit var stripe: Stripe
    lateinit var subscription: Subscription
    lateinit var superhero: Superhero
    lateinit var superSmashBros: SuperSmashBros
    lateinit var swordArtOnline: SwordArtOnline
    lateinit var team: Team
    lateinit var theExpanse: TheExpanse
    lateinit var theITCrowd: TheITCrowd
    lateinit var theThickOfIt: TheThickOfIt
    lateinit var twinPeaks: TwinPeaks
    lateinit var umphreysMcgee: UmphreysMcgee
    lateinit var university: University
    lateinit var vehicle: Vehicle
    lateinit var ventureBros: VentureBros
    lateinit var verbs: Verbs
    lateinit var vForVendetta: VForVendetta
    lateinit var witcher: Witcher
    lateinit var worldCup: WorldCup
    lateinit var worldOfWarcraft: WorldOfWarcraft
    lateinit var yoda: Yoda
    lateinit var zelda: Zelda

    @JvmOverloads
    fun init(locale: Locale = Locale.ENGLISH): Faker {
        fakerService = FakerService(locale)

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
//        dota = Dota(fakerService)
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
        invoice = Invoice(fakerService)
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
