package io.github.serpro69.kfaker.tv.integration

import io.github.serpro69.kfaker.test.helper.`every public function in each provider is invoked without exceptions`
import io.github.serpro69.kfaker.test.helper.`faker instance is initialized with custom locale`
import io.github.serpro69.kfaker.tv.TvShowsFaker
import io.github.serpro69.kfaker.tv.faker
import io.kotest.core.spec.style.DescribeSpec

class TvShowsFakerIT :
    DescribeSpec({
        `every public function in each provider is invoked without exceptions`(TvShowsFaker())

        `faker instance is initialized with custom locale` { faker {} }
    })
