"""Print Task 25 reference vectors; Python 3 standard library only, no Kotlin inputs.

Unbounded Python integers and divmod express the mathematical specification.
This is an offline test oracle, never part of the runtime or generated at test time.
SplitMix64 constants: Sebastiano Vigna's public-domain https://prng.di.unimi.it/splitmix.c.
"""

import struct


MODULUS = 2**64
SEEDS = (0, 1, -1, -(2**63), 2**63 - 1, 0x0123456789ABCDEF)


def words(seed):
    """Evaluate each stream position directly, without mutable fixed-width state."""
    position = 1
    while True:
        value = (seed + position * 0x9E3779B97F4A7C15) % MODULUS
        value = ((value ^ (value // 2**30)) * 0xBF58476D1CE4E5B9) % MODULUS
        value = ((value ^ (value // 2**27)) * 0x94D049BB133111EB) % MODULUS
        yield value ^ (value // 2**31)
        position += 1


def below(stream, width):
    _, discarded = divmod(MODULUS, width)
    while True:
        value = next(stream)
        if value >= discarded:
            return value % width


def signed(value, bits):
    return value if value < 2 ** (bits - 1) else value - 2**bits


def mixed(seed):
    stream = words(seed)
    result = [str(signed(next(stream) // 2**32, 32)), str(signed(next(stream), 64))]
    result += [str(below(stream, 10)), str(below(stream, 2**62 + 1))]
    result += [str(-(2**31) + below(stream, 2**32))]
    result += [str(-(2**63) + below(stream, MODULUS))]
    result += [str(-17 + below(stream, 37)), str(-4_000_000_000 + below(stream, 9_000_000_001))]
    result += [str(next(stream) >= 2**63).lower()]
    result += [struct.pack('>f', (next(stream) // 2**40) / 2**24).hex()]
    result += [struct.pack('>d', (next(stream) // 2**11) / 2**53).hex()]
    result += [(next(stream).to_bytes(8, 'little') + next(stream).to_bytes(8, 'little'))[:11].hex()]
    permutation = list(range(8))
    for index in range(7, 0, -1):
        other = below(stream, index + 1)
        permutation[index], permutation[other] = permutation[other], permutation[index]
    result += [','.join(map(str, permutation)), f'{next(stream):016x}']
    return '|'.join(result)


def main():
    for seed in SEEDS:
        stream = words(seed)
        print(seed, 'raw', ' '.join(f'{next(stream):016x}' for _ in range(5)))
        print(seed, 'mixed', mixed(seed))


if __name__ == '__main__':
    main()
