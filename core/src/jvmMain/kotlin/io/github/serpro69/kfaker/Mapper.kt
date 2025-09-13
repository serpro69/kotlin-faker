package io.github.serpro69.kfaker

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.io.InputStream

internal object Mapper {
    private val mapper = ObjectMapper()

    init {
        mapper.registerModule(KotlinModule.Builder().build())
    }

    fun <T> readValue(inputStream: InputStream, type: Class<T>): T =
        mapper.readerFor(type).readValue(inputStream)
}
