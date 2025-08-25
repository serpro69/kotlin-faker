package io.github.serpro69.kfaker.edu.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.JOB] category. */
@Suppress("unused")
class Job internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Job>(fakerService) {
    override val yamlCategory = YamlCategory.JOB
    override val localUniqueDataProvider = LocalUniqueDataProvider<Job>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun field() = resolve("field")

    fun seniority() = resolve("seniority")

    fun position() = resolve("position")

    fun keySkills() = resolve("key_skills")

    fun employmentType() = resolve("employment_type")

    fun educationLevel() = resolve("education_level")

    fun title() = resolve("title")
}
