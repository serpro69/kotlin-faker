package io.github.serpro69.kfaker.pictures

import io.github.serpro69.kfaker.AbstractFaker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.FakerDsl
import io.github.serpro69.kfaker.fakerConfig
import io.github.serpro69.kfaker.pictures.provider.UiFaces
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

/** Typealias for the [PicturesFaker] */
typealias Faker = PicturesFaker

/**
 * Provides access to fake data generators within the Pictures domain.
 *
 * Each category (generator) from this [PicturesFaker] is represented by a property that (usually)
 * has the same name as the `.yml` dictionary file.
 *
 * @property unique global provider for generation of unique values.
 */
@Suppress("unused")
class PicturesFaker @JvmOverloads constructor(config: FakerConfig = fakerConfig {}) :
    AbstractFaker(config) {

    val uiFaces: UiFaces by lazy { UiFaces(randomService) }

    @FakerDsl
    /** DSL builder for creating instances of [Faker] */
    class Builder internal constructor() : AbstractFaker.Builder<Faker>() {

        /** Builds an instance of [Faker] with this [config]. */
        override fun build(): Faker = Faker(config)
    }
}

/**
 * Applies the [block] function to [PicturesFaker.Builder] and returns as an instance of
 * [PicturesFaker] from that builder.
 */
fun faker(block: PicturesFaker.Builder.() -> Unit): PicturesFaker =
    PicturesFaker.Builder().apply(block).build()

/** Returns the [input] as 32bit RGBA [ByteArray] */
internal fun convertTo32BitRGBA(input: ByteArray): ByteArray {
    // read the input ByteArray into a BufferedImage
    val inputImage =
        ImageIO.read(ByteArrayInputStream(input))
            ?: throw IllegalArgumentException("Invalid image data")
    // create a new BufferedImage in 32-bit RGBA format
    val rgbaImage = BufferedImage(inputImage.width, inputImage.height, BufferedImage.TYPE_INT_ARGB)
    // draw the input image onto the RGBA BufferedImage
    val graphics = rgbaImage.createGraphics()
    graphics.drawImage(inputImage, 0, 0, null)
    graphics.dispose()
    // extract raw RGBA data from the BufferedImage
    val width = rgbaImage.width
    val height = rgbaImage.height
    val pixelData = IntArray(width * height)
    rgbaImage.getRGB(0, 0, width, height, pixelData, 0, width)
    // convert the IntArray to a ByteArray
    val byteArrayOutput = ByteArrayOutputStream()
    for (pixel in pixelData) {
        byteArrayOutput.write((pixel shr 16) and 0xFF) // Red
        byteArrayOutput.write((pixel shr 8) and 0xFF) // Green
        byteArrayOutput.write(pixel and 0xFF) // Blue
        byteArrayOutput.write((pixel shr 24) and 0xFF) // Alpha
    }
    return byteArrayOutput.toByteArray()
}
