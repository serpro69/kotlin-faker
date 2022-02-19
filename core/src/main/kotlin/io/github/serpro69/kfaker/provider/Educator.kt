package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [CategoryName.EDUCATOR] category.
 */
@Suppress("unused")
class Educator internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Educator>(fakerService) {
    override val categoryName = CategoryName.EDUCATOR
    override val localUniqueDataProvider = LocalUniqueDataProvider<Educator>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

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
        replaceWith = ReplaceWith("tertiaryUniversityType()"),
        level = DeprecationLevel.WARNING
    )
    fun universityType() = resolve("tertiary", "university_type")

    fun tertiaryUniversityType() = resolve("tertiary", "university_type")
    fun tertiaryDegreeType() = resolve("tertiary", "degree", "type")
    fun tertiaryDegreeCourseNumber() = with(fakerService) {
        resolve("tertiary", "degree", "course_number")
            .numerify()
    }
}
