package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.EDUCATOR] category.
 */
@Suppress("unused")
class Educator internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Educator>(fakerService) {
    override val category = YamlCategory.EDUCATOR
    override val localUniqueDataProvider = LocalUniqueDataProvider<Educator>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    val tertiary = Tertiary(fakerService)

    fun schoolName() = resolve("school_name")
    fun secondary() = resolve("secondary")

    fun university() = resolve("university")

    fun secondarySchool() = resolve("secondary_school")
    fun campus() = resolve("campus")
    fun subject() = resolve("subject")

    fun degree() = resolve("degree")

    fun courseName() = resolve("course_name")

    @Deprecated(
        message = "This is deprecated and will be removed in future releases",
        replaceWith = ReplaceWith("tertiary.universityType()"),
        level = DeprecationLevel.WARNING
    )
    fun universityType() = resolve("tertiary", "university_type")
}

class Tertiary internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Tertiary>(fakerService) {
    override val category = YamlCategory.EDUCATOR
    override val localUniqueDataProvider = LocalUniqueDataProvider<Tertiary>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    val degree = Degree(fakerService)

    fun universityType() = resolve("tertiary", "university_type")
}

class Degree internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Degree>(fakerService) {
    override val category = YamlCategory.EDUCATOR
    override val localUniqueDataProvider = LocalUniqueDataProvider<Degree>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun type() = resolve("tertiary", "degree", "type")
    fun courseNumber() = with(fakerService) {
        resolve("tertiary", "degree", "course_number")
            .numerify()
    }
}
