package io.github.serpro69.kfaker.tech.integration

import io.github.serpro69.kfaker.tech.TechFaker
import io.github.serpro69.kfaker.tech.faker
import io.github.serpro69.kfaker.test.helper.`every public function in each provider is invoked without exceptions`
import io.github.serpro69.kfaker.test.helper.`faker instance is initialized with custom locale`
import io.kotest.core.spec.style.DescribeSpec

class TechFakerIT :
    DescribeSpec({
        `every public function in each provider is invoked without exceptions`(TechFaker())

        `faker instance is initialized with custom locale` { faker {} }
    })
