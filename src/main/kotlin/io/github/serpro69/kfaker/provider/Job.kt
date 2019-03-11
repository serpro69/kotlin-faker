package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.JOB] category.
 */
class Job internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.JOB

    val field = resolve { fakerService.resolve(Faker, it, "field") }
    val seniority = resolve { fakerService.resolve(Faker, it, "seniority") }
    val position = resolve { fakerService.resolve(Faker, it, "position") }
    val keySkills = resolve { fakerService.resolve(Faker, it, "key_skills") }
    val employmentType = resolve { fakerService.resolve(Faker, it, "employment_type") }
    val educationLevel = resolve { fakerService.resolve(Faker, it, "education_level") }
    val title = resolve { fakerService.resolve(Faker, it, "title") }
}
