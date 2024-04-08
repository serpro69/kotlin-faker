package io.github.serpro69.kfaker.kotest

import io.github.serpro69.kfaker.AbstractFaker
import kotlin.reflect.KClass

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FILE)
annotation class FakerArb(vararg val fakers: KClass<out AbstractFaker>)
