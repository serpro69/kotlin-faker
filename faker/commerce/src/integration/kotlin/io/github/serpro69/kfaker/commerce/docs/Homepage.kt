package io.github.serpro69.kfaker.commerce.docs

import io.github.serpro69.kfaker.commerce.CommerceFaker
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import java.util.*

/**
 * Documentation code snippets for the docs website homepage.
 *
 * This approach has the benefit over using something like code-blocks in .md files directly in a way
 * that this becomes a "live-documentation",
 * and additionally it's also being tested since this is, after all, a test class.
 *
 * Usage:
 * ❶ Wrap each code snippet in comments like `// --8<-- [start:snippet_name` and `// --8<-- [end:snippet_name`]]
 * ❷ Code snippets can be referenced from the docs using the {% snippet snippet_name %} tag.
 * ❸ (See https://orchid.run/plugins/orchidsnippets for snippets docs)
 */
@DisplayName("Snippets used in Orchid docs 'homepage'")
class Homepage : DescribeSpec({
    describe("CommerceFaker") {
        context("Fake Data Providers in Commerce domain") {
            it("should print a SWIFT BIC code") {
                // --8<-- [start:commerce_faker_one]
                val faker = CommerceFaker()
                faker.bank.swiftBic() // => AACCGB21
                // --8<-- [end:commerce_faker_one]
            }
        }
    }
})
