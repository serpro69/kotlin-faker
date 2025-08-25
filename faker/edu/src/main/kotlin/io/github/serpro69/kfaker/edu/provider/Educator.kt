package io.github.serpro69.kfaker.edu.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.EDUCATOR] category. */
@Suppress("unused")
class Educator internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Educator>(fakerService) {
    override val yamlCategory = YamlCategory.EDUCATOR
    override val localUniqueDataProvider = LocalUniqueDataProvider<Educator>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    val tertiary by lazy { Tertiary(fakerService) }

    fun schoolName() = resolve("school_name")

    fun secondary() = resolve("secondary")

    fun primary() = resolve("primary")

    fun university() = resolve("university")

    fun secondarySchool() = resolve("secondary_school")

    fun primarySchool() = resolve("primary_school")

    fun campus() = resolve("campus")

    fun subject() = resolve("subject")

    fun degree() = resolve("degree")

    fun courseName() = with(fakerService) { resolve("course_name").numerify() }
}

@Suppress("unused")
class Tertiary internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Tertiary>(fakerService) {
    override val yamlCategory = YamlCategory.EDUCATOR
    override val localUniqueDataProvider = LocalUniqueDataProvider<Tertiary>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    val degree by lazy { Degree(fakerService) }

    fun universityType() = resolve("tertiary", "university_type")
}

@Suppress("unused")
class Degree internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Degree>(fakerService) {
    override val yamlCategory = YamlCategory.EDUCATOR
    override val localUniqueDataProvider = LocalUniqueDataProvider<Degree>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun type() = resolve("tertiary", "degree", "type")

    fun courseNumber() =
        with(fakerService) { resolve("tertiary", "degree", "course_number").numerify() }
}
