package io.github.serpro69.kfaker.exception

@Suppress("unused")
class DictionaryKeyNotFoundException : Exception {

    constructor(message: String) : super(message)

    constructor(message: String, throwable: Throwable) : super(message, throwable)

    constructor(throwable: Throwable) : super(throwable)
}
