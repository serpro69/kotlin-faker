package com.github.sergio.igwt.kfaker

import io.kotlintest.*
import io.kotlintest.matchers.haveKeys
import io.kotlintest.specs.*

internal class FakerServiceTest : FreeSpec() {

    init {
        "Test loading dictionary" - {

            "Test getting dictionary with default locale" {
                val service = FakerService()
                val dictionary = service.load()

                dictionary should haveKeys("address")
            }
        }
    }
}
