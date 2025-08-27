package io.github.serpro69.kfaker.tv.integration.docs

import io.github.serpro69.kfaker.tv.TvShowsFaker
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import java.util.*

/**
 * Documentation code snippets for the docs website homepage.
 *
 * This approach has the benefit over using something like code-blocks in .md files directly in a
 * way that this becomes a "live-documentation", and additionally it's also being tested since this
 * is, after all, a test class.
 *
 * Usage:
 * ❶ Wrap each code snippet in comments like
 *   `// --8<-- [start:snippet_name]`
 *   and `// --8<-- [end:snippet_name]`
 * ❷ Code snippets can be referenced from the docs
 *   using the `--8<-- "path/to/file:snippet_name"`.
 * ❸ (See https://facelessuser.github.io/pymdown-extensions/extensions/snippets/
 *   for snippets docs)
 */
@DisplayName("Snippets used in documentation 'homepage'")
class Homepage :
    DescribeSpec({
        describe("TvShowsFaker") {
            context("Fake Data Providers in TvShows domain") {
                it("should print a character name from Friends") {
                    // --8<-- [start:tvshows_faker_one]
                    val faker = TvShowsFaker()
                    faker.friends.characters() // => Phoebe Buffay
                    // --8<-- [end:tvshows_faker_one]
                }
            }
        }
    })
