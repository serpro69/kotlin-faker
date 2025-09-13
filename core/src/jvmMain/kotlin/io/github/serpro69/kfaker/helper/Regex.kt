package io.github.serpro69.kfaker.helper

/**
 * A list of regular expressions for reserved networks addresses
 *
 * Borrowed from
 * https://github.com/faker-ruby/faker/blob/b0ab11d93dd09c513a1009a28325ff6efdddf9a2/lib/faker/default/internet.rb#L349
 */
internal val reservedNets: List<Regex> =
    listOf(
        Regex("""^0\./"""), // 0.0.0.0      - 0.255.255.255
        Regex("""^192\.0\.2\."""), // 192.0.2.0    - 192.0.2.255
        Regex("""^192\.88\.99\."""), // 192.88.99.0  - 192.88.99.255
        Regex("""^198\.51\.100\."""), // 198.51.100.0 - 198.51.100.255
        Regex("""^203\.0\.113\."""), // 203.0.113.0  - 203.0.113.255
        Regex("""^(22[4-9]|23\d)\."""), // 224.0.0.0    - 239.255.255.255
        Regex("""^(24\d|25[0-5])\."""), // 240.0.0.0    - 255.255.255.254 and 255.255.255.255
    )

/**
 * A list of regular expressions for private networks addresses
 *
 * Borrowed from
 * https://github.com/faker-ruby/faker/blob/b0ab11d93dd09c513a1009a28325ff6efdddf9a2/lib/faker/default/internet.rb#L317
 */
internal val privateNets: List<Regex> =
    listOf(
        Regex("""^10\."""), // 10.0.0.0    - 10.255.255.255
        Regex("""^100\.(6[4-9]|[7-9]\d|1[0-1]\d|12[0-7])\."""), // 100.64.0.0  - 100.127.255.255
        Regex("""^127\."""), // 127.0.0.0   - 127.255.255.255
        Regex("""^169\.254\."""), // 169.254.0.0 - 169.254.255.255
        Regex("""^172\.(1[6-9]|2\d|3[0-1])\."""), // 172.16.0.0  - 172.31.255.255
        Regex("""^192\.0\.0\."""), // 192.0.0.0   - 192.0.0.255
        Regex("""^192\.168\."""), // 192.168.0.0 - 192.168.255.255
        Regex("""^198\.(1[8-9])\."""), // 198.18.0.0  - 198.19.255.255
    )

/**
 * Returns true if the [addr] contains at least one match in the [privateNets] regular expressions
 * list.
 */
internal val isPrivateNet: (addr: (String)) -> Boolean = { privateNets.any(it::contains) }

/**
 * Returns true if the [addr] contains at least one match in [privateNets] or [reservedNets] regular
 * expressions lists.
 */
internal val isReservedNet: (addr: (String)) -> Boolean = {
    listOf(privateNets, reservedNets).flatten().any(it::contains)
}
