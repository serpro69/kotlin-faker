package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.JOB] category.
 */
@Suppress("unused")
class Job internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Job>(fakerService) {
    override val category = YamlCategory.JOB
    override val localUniqueDataProvider = LocalUniqueDataProvider<Job>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun field() = resolve("field")
    fun seniority() = resolve("seniority")
    fun position() = resolve("position")
    fun keySkills() = resolve("key_skills")
    fun employmentType() = resolve("employment_type")
    fun educationLevel() = resolve("education_level")
    fun title() = resolve("title")
}
