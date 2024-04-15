package io.github.serpro69.kfaker.kotest.extensions

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.symbol.KSAnnotated

@OptIn(KspExperimental::class)
internal inline fun <reified T : Annotation> KSAnnotated.hasAnnotation(predicate: (T) -> Boolean = { true }): Boolean =
    getAnnotationsByType(T::class).firstOrNull() != null
