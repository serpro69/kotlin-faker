package io.github.serpro69.kfaker

import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.dataformat.yaml.*
import com.fasterxml.jackson.module.kotlin.*
import java.io.*

internal object Mapper {
    private val mapper = ObjectMapper(YAMLFactory())

    init {
        mapper.registerModule(KotlinModule())
    }

    fun <T> readValue(inputStream: InputStream, type: Class<T>): T = mapper.readerFor(type).readValue(inputStream)
}
