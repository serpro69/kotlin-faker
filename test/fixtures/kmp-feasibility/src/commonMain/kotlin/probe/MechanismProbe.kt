package probe

import probe.payload.PayloadStore
import probe.payload.localeLayers
import probe.payload.payloadProjections
import probe.payload.payloadSources
import probe.random.RandomSampling
import probe.regex.PortableRegex

/** Local publication boundary only. Never promote this facade into production artifacts. */
class MechanismProbe(seed: Long) {
    private val store = PayloadStore()
    private val random = RandomSampling(seed)
    private val regex = PortableRegex(random)

    val sourceCount: Int get() = payloadSources.size
    val localeCount: Int get() = localeLayers.size
    val projectionCount: Int get() = payloadProjections.size
    val decodedCount: Int get() = store.decodedCount

    fun sourcePath(index: Int): String = payloadSources[payloadProjections[index].sourceId].path
    fun category(index: Int): String? = payloadProjections[index].category
    fun encodedProjection(index: Int): String = payloadProjections[index].json()
    fun decodedProjection(sourcePath: String, category: String?): String =
        store.projection(sourcePath, category).toString()

    fun nextLong(): Long = random.nextLong()
    fun nextInt(bound: Int): Int = random.nextInt(bound)
    fun nextDouble(): Double = random.nextDouble()
    fun regexify(pattern: String): String = regex.generate(pattern)
}
