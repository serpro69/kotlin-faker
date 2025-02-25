package io.github.serpro69.kfaker.provider

import java.time.LocalDate
import kotlin.random.asKotlinRandom
import kotlin.random.Random

/**
 * [FakeDataProvider] implementation class for functionality not covered by the standard dictionary files.
 *
 *
 */
@Suppress("unused")
class Person internal constructor(private val random: Random) {

    /**
     * Returns a pseudo-random birthDate as [LocalDate] for the given [age] value.
     *
     * If the [at] date parameter is not provided, then [LocalDate.now] is used
     * to calculate the "lower bound" for the resulting birthDate.
     * Assuming the [age] value of `30`, the resulting date will be in the range of:
     *
     * ```
     * val start = LocalDate.now().minusYears(30)
     * val end = LocalDate.now().minusYears(29).minusDays(1)
     * ```
     *
     * @param age the person's age for which the birthDate will be calculated
     * @param at a date at which the person was of the specified [age].
     * Equals to the result of [LocalDate.now] by default
     *
     * @return a pseudo-randomly calculated birthDate as instance of [LocalDate]
     */
    @JvmOverloads
    fun birthDate(age: Long, at: LocalDate = LocalDate.now()): LocalDate {
        val startDate = at.minusYears(age + 1)
        val endDate = startDate.plusYears(1).minusDays(1)
        val startMaxDays = if (startDate.isLeapYear) 366 else 365
        val lower = LocalDate.ofYearDay(startDate.year, (startDate.dayOfYear..startMaxDays).random(random))
        val upper = LocalDate.ofYearDay(endDate.year, (1..endDate.dayOfYear).random(random))
        val dates = if (lower.month == at.month && lower.dayOfMonth == at.dayOfMonth) {
            lower.plusDays(1) to upper
        } else if (upper.month == at.month && upper.dayOfMonth == at.dayOfMonth) {
            lower to upper.plusDays(1)
        } else lower to upper
        return dates.random()
    }

    private fun Pair<LocalDate, LocalDate>.random() = listOf(first, second).random(random)
}
