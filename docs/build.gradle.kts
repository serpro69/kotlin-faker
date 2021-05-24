import java.net.URI
import kotlin.random.Random

plugins {
    id("com.eden.orchidPlugin") version "0.21.1"
}

repositories {
    jcenter()
    maven { url = URI("https://kotlin.bintray.com/kotlinx") }
}

dependencies {
    val orchidRuntime by configurations
    orchidRuntime("io.github.javaeden.orchid:OrchidDocs:0.21.1")
    orchidRuntime("io.github.javaeden.orchid:OrchidKotlindoc:0.21.1")
    orchidRuntime("io.github.javaeden.orchid:OrchidPluginDocs:0.21.1")
}

orchid {
    theme = "Editorial"
    baseUrl = "https://serpro69.github.io/kotlin-faker"
    version = "1.0.0"
    port = Random.nextInt(20000, 30000)
}
