package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.JOB] category.
 */
@Suppress("unused")
class Job internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Job>(fakerService) {
    override val categoryName = CategoryName.JOB
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val field = resolve("field")
    val seniority = resolve("seniority")
    val position = resolve("position")
    val keySkills = resolve("key_skills")
    val employmentType = resolve("employment_type")
    val educationLevel = resolve("education_level")
    val title = resolve("title")
}
