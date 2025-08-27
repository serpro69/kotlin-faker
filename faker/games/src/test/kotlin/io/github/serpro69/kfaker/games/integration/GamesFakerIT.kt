package io.github.serpro69.kfaker.games.integration

import io.github.serpro69.kfaker.games.GamesFaker
import io.github.serpro69.kfaker.games.faker
import io.github.serpro69.kfaker.test.helper.`every public function in each provider is invoked without exceptions`
import io.github.serpro69.kfaker.test.helper.`faker instance is initialized with custom locale`
import io.kotest.core.spec.style.DescribeSpec

class GamesFakerIT :
    DescribeSpec({
        `every public function in each provider is invoked without exceptions`(GamesFaker())

        `faker instance is initialized with custom locale` { faker {} }
    })
