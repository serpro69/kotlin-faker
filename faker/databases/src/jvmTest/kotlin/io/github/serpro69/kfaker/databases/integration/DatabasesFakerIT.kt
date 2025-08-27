package io.github.serpro69.kfaker.databases.integration

import io.github.serpro69.kfaker.databases.DatabasesFaker
import io.github.serpro69.kfaker.databases.faker
import io.github.serpro69.kfaker.test.helper.`every public function in each provider is invoked without exceptions`
import io.github.serpro69.kfaker.test.helper.`faker instance is initialized with custom locale`
import io.kotest.core.spec.style.DescribeSpec

class DatabasesFakerIT :
    DescribeSpec({
        `every public function in each provider is invoked without exceptions`(DatabasesFaker())

        `faker instance is initialized with custom locale` { faker {} }
    })
