package probe.random

/** A whole-word source so sampling can be verified independently of the seeded generator. */
internal fun interface RandomWords {
    fun nextWord(): ULong
}

/**
 * Fixed-increment SplitMix64, adapted from Sebastiano Vigna's 2015 public-domain reference:
 * https://prng.di.unimi.it/splitmix.c
 *
 * The seed's two's-complement bits initialize state directly, without warmup or family mixing.
 * ULong addition/multiplication wrap modulo 2^64; its right shifts are logical. Sequential use
 * only; this is neither cryptographic randomness nor a cross-release sequence guarantee.
 */
internal class SplitMix64(seed: Long) : RandomWords {
    private var state = seed.toULong()

    override fun nextWord(): ULong {
        state += 0x9e3779b97f4a7c15uL
        var word = state
        word = (word xor (word shr 30)) * 0xbf58476d1ce4e5b9uL
        word = (word xor (word shr 27)) * 0x94d049bb133111ebuL
        return word xor (word shr 31)
    }
}
