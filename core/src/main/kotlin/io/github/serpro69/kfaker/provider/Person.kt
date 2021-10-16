package io.github.serpro69.kfaker.provider

import java.time.LocalDate
import java.time.Month
import java.util.*
import kotlin.random.asKotlinRandom

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
        val startDate = at.minusYears(age)
        val endDate = startDate.plusYears(1).minusDays(1)
        val lower = LocalDate.ofYearDay(startDate.year, (startDate.dayOfYear..365).random(random.asKotlinRandom()))
        val upper = LocalDate.ofYearDay(endDate.year, (1..endDate.dayOfYear).random(random.asKotlinRandom()))
        return (lower to upper).random()
    }

    /**
     * Returns a pseudo-random birthDate as [LocalDate] for the given [age] and [month] values.
     *
     * If the [at] date parameter is not provided, then [LocalDate.now] is used to calculate the birth year.
     * Assuming the [age] value of `30` and [month] of [Month.DECEMBER],
     * the resulting date will be in the range of:
     *
     * ```
     * val start = LocalDate.now().minusYears(30).withMonth(12).withDayOfMonth(1)
     * val end = LocalDate.now().minusYears(30).withMonth(12).withDayOfMonth(31)
     * ```
     *
     * @param age the person's age for which the birthDate will be calculated
     * @param month the month for which the birthDate will be calculated
     * @param at a date at which the person was of the specified [age].
     * Equals to the result of [LocalDate.now] by default
     *
     * @return a pseudo-randomly calculated birthDate as instance of [LocalDate]
     */
    @JvmOverloads
    fun birthDate(age: Long, month: Month, at: LocalDate = LocalDate.now()): LocalDate {
        return birthDate(age, listOf(month), at = at)
    }

    /**
     * Returns a pseudo-random birthDate as [LocalDate] for the given [age] and [months] values.
     *
     * If the [at] date parameter is not provided, then [LocalDate.now] is used to calculate the birth year.
     * Assuming the [age] value of `30` and [months] having [Month.DECEMBER] value,
     * the resulting date will be in the range of:
     *
     * ```
     * val start = LocalDate.now().minusYears(30).withMonth(12).withDayOfMonth(1)
     * val end = LocalDate.now().minusYears(30).withMonth(12).withDayOfMonth(31)
     * ```
     *
     * @param age the person's age for which the birthDate will be calculated
     * @param months months for which the birthDate will be calculated
     * @param at a date at which the person was of the specified [age].
     * Equals to the result of [LocalDate.now] by default
     *
     * @return a pseudo-randomly calculated birthDate as instance of [LocalDate]
     */
    @JvmOverloads
    fun birthDate(age: Long, vararg months: Month, at: LocalDate = LocalDate.now()): LocalDate {
        val month = months.random(random.asKotlinRandom())
        val start = at.minusYears(age).withMonth(month.value)
        val year = start.year
        val day = (1..month.length(start.isLeapYear)).random(random.asKotlinRandom())
        return LocalDate.of(year, month, day)
    }

    /**
     * Returns a pseudo-random birthDate as [LocalDate] for the given [age] and [months] values.
     *
     * If the [at] date parameter is not provided, then [LocalDate.now] is used to calculate the birth year.
     * Assuming the [age] value of `30` and [months] having [Month.DECEMBER] value,
     * the resulting date will be in the range of:
     *
     * ```
     * val start = LocalDate.now().minusYears(30).withMonth(12).withDayOfMonth(1)
     * val end = LocalDate.now().minusYears(30).withMonth(12).withDayOfMonth(31)
     * ```
     *
     * @param age the person's age for which the birthDate will be calculated
     * @param months months for which the birthDate will be calculated
     * @param at a date at which the person was of the specified [age].
     * Equals to the result of [LocalDate.now] by default
     *
     * @return a pseudo-randomly calculated birthDate as instance of [LocalDate]
     */
    @JvmOverloads
    fun birthDate(age: Long, months: List<Month>, at: LocalDate = LocalDate.now()): LocalDate {
        return birthDate(age, *months.toTypedArray(), at = at)
    }

    private fun Pair<LocalDate, LocalDate>.random() = listOf(first, second).random(random.asKotlinRandom())
}
