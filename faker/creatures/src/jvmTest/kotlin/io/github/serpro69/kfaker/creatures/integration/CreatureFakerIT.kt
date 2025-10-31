package io.github.serpro69.kfaker.creatures.integration

import io.github.serpro69.kfaker.creatures.CreaturesFaker
import io.github.serpro69.kfaker.creatures.faker
import io.github.serpro69.kfaker.test.helper.`every public function in each provider is invoked without exceptions`
import io.github.serpro69.kfaker.test.helper.`faker instance is initialized with custom locale`
import io.kotest.core.spec.style.DescribeSpec

class CreatureFakerIT :
    DescribeSpec({
        `every public function in each provider is invoked without exceptions`(CreaturesFaker())

        `faker instance is initialized with custom locale` { faker {} }
    })
