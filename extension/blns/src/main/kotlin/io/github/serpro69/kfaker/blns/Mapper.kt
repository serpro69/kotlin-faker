package io.github.serpro69.kfaker.blns

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.io.InputStream

internal object Mapper {
    private val mapper = ObjectMapper()

    init {
        mapper.registerModule(KotlinModule.Builder().build())
    }

    fun <T> readValue(inputStream: InputStream, typeRef: TypeReference<T>): T = mapper.readValue(inputStream, typeRef)
}
