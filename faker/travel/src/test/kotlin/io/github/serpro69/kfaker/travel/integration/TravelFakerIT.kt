package io.github.serpro69.kfaker.travel.integration

import io.github.serpro69.kfaker.test.helper.`every public function in each provider is invoked without exceptions`
import io.github.serpro69.kfaker.test.helper.`faker instance is initialized with custom locale`
import io.github.serpro69.kfaker.travel.TravelFaker
import io.github.serpro69.kfaker.travel.faker
import io.kotest.core.spec.style.DescribeSpec

class TravelFakerIT :
    DescribeSpec({
        `every public function in each provider is invoked without exceptions`(TravelFaker())

        `faker instance is initialized with custom locale` { faker {} }
    })
