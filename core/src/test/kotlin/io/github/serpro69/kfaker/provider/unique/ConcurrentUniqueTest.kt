package io.github.serpro69.kfaker.provider.unique

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.fakerConfig
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

/**
 * Test to reproduce multi-threading issues with unique value generation.
 *
 * This test demonstrates that even with ThreadLocal<Faker>, the unique value
 * generation has race conditions that can cause:
 * - Duplicate values across threads
 * - RetryLimitException due to corrupted state
 */
class ConcurrentUniqueTest : DescribeSpec({

    fun createFaker(): Faker {
        val config = fakerConfig {
            uniqueGeneratorRetryLimit = 10_000
        }
        val newFaker = Faker(config)
        newFaker.unique.configuration {
            enable(newFaker::address)
            enable(newFaker::name)
            enable(newFaker::internet)
        }
        return newFaker
    }

    val threadLocalFaker = ThreadLocal.withInitial { createFaker() }

    describe("Concurrent unique value generation") {
        it("should not have duplicates within a single thread") {
            val threadCount = 10
            val iterationsPerThread = 100
            val executor = Executors.newFixedThreadPool(threadCount)
            val latch = CountDownLatch(threadCount)

            // Track duplicates found within each thread
            val threadDuplicates = ConcurrentHashMap<Int, Set<String>>()

            try {
                repeat(threadCount) { threadIndex ->
                    executor.submit {
                        try {
                            val faker = threadLocalFaker.get()
                            val threadValues = mutableSetOf<String>()
                            val dups = mutableSetOf<String>()

                            repeat(iterationsPerThread) {
                                val value = faker.address.city()
                                if (!threadValues.add(value)) {
                                    // Duplicate within this thread!
                                    dups.add(value)
                                }
                            }

                            if (dups.isNotEmpty()) {
                                threadDuplicates[threadIndex] = dups
                            }
                        } finally {
                            latch.countDown()
                        }
                    }
                }

                latch.await()

                // Report findings
                if (threadDuplicates.isNotEmpty()) {
                    println("Duplicates found within threads:")
                    threadDuplicates.forEach { (threadId, dups) ->
                        println("  Thread $threadId: ${dups.size} duplicates - ${dups.take(3)}")
                    }
                } else {
                    println("No duplicates found within any thread - fix is working!")
                }

                // Each thread should have no duplicates within its own Faker instance
                threadDuplicates.size shouldBe 0

            } finally {
                executor.shutdown()
                threadLocalFaker.remove()
            }
        }

        it("should reproduce RetryLimitException with concurrent access") {
            val threadCount = 5
            val iterationsPerThread = 50
            val executor = Executors.newFixedThreadPool(threadCount)
            val latch = CountDownLatch(threadCount)

            val exceptions = ConcurrentHashMap.newKeySet<String>()
            val generated = java.util.concurrent.atomic.AtomicInteger(0)

            try {
                repeat(threadCount) {
                    executor.submit {
                        try {
                            val faker = threadLocalFaker.get()
                            repeat(iterationsPerThread) {
                                try {
                                    faker.name.firstName()
                                    generated.incrementAndGet()
                                } catch (e: Exception) {
                                    exceptions.add("${e::class.simpleName}: ${e.message}")
                                }
                            }
                        } finally {
                            latch.countDown()
                        }
                    }
                }

                latch.await()

                println("Total values generated: ${generated.get()}")
                println("Exceptions caught: ${exceptions.size}")
                exceptions.forEach { exc -> println("  - $exc") }

                // With the bug, we might see RetryLimitException or other issues
                exceptions.size shouldBe 0

            } finally {
                executor.shutdown()
                threadLocalFaker.remove()
            }
        }

        it("should handle concurrent enable and value generation") {
            val threadCount = 8
            val executor = Executors.newFixedThreadPool(threadCount)
            val latch = CountDownLatch(threadCount)

            val allCities = ConcurrentHashMap.newKeySet<String>()
            val exceptions = ConcurrentHashMap.newKeySet<String>()

            try {
                repeat(threadCount) { threadIndex ->
                    executor.submit {
                        try {
                            // Each thread creates its own faker and immediately uses it
                            val faker = createFaker()

                            repeat(50) {
                                try {
                                    val city = faker.address.city()
                                    allCities.add(city)
                                } catch (e: Exception) {
                                    exceptions.add("Thread $threadIndex: ${e::class.simpleName}: ${e.message}")
                                }
                            }
                        } finally {
                            latch.countDown()
                        }
                    }
                }

                latch.await()

                println("Total unique cities: ${allCities.size}")
                println("Exceptions: ${exceptions.size}")
                exceptions.forEach { exc -> println("  - $exc") }

                // Should generate unique values without exceptions
                exceptions.size shouldBe 0

            } finally {
                executor.shutdown()
            }
        }
    }
})
