package io.github.serpro69.kfaker.docs;

import io.github.serpro69.kfaker.Faker;
import io.github.serpro69.kfaker.FakerConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kotlin.random.RandomKt.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Snippets used in Orchid docs 'Faker Configuration' wiki page")
class FakerConfigurationJ {

    @DisplayName("two faker instances with same 'random' should output same values - java")
    @Test
    void sameRandomConfigurationForTwoFakerInstances() {
        // --8<-- [start:faker_config_one_java]
        FakerConfig.Builder configBuilder = FakerConfig.builder();

        Faker faker = new Faker(configBuilder.withRandom(Random(42)).build());
        String city1 = faker.getAddress().city();
        String name1 = faker.getName().name();

        Faker otherFaker = new Faker(configBuilder.withRandom(Random(42)).build());
        String city2 = otherFaker.getAddress().city();
        String name2 = otherFaker.getName().name();

        assertEquals(city1, city2);
        assertEquals(name1, name2);
        // --8<-- [end:faker_config_one_java]
    }

    @DisplayName("two faker instances with same 'randomSeed' should output same values - java")
    @Test
    void sameRandomSeedConfiguration() {
        // --8<-- [start:faker_config_two_java]
        FakerConfig config = FakerConfig.builder()
            .withRandomSeed(42)
            .build();
        Faker faker = new Faker(config);
        // --8<-- [end:faker_config_two_java]

        String city1 = faker.getAddress().city();
        String name1 = faker.getName().name();

        Faker otherFaker = new Faker(FakerConfig.builder().withRandomSeed(42).build());
        String city2 = otherFaker.getAddress().city();
        String name2 = otherFaker.getName().name();

        assertEquals(city1, city2);
        assertEquals(name1, name2);
    }

    @DisplayName("random' should be ignored if 'randomSeed' is specified - java")
    @Test
    void ignoreRandomIfRandomSeedIsSet() {
        // --8<-- [start:faker_config_three_java]
        FakerConfig config = FakerConfig.builder()
            .withRandom(Random(123))
            .withRandomSeed(42)
            .build();
        Faker faker = new Faker(config);
        String city1 = faker.getAddress().city();

        FakerConfig otherConfig = FakerConfig.builder()
            .withRandom(Random(42))
            .build();
        Faker otherFaker = new Faker(otherConfig);
        String city2 = otherFaker.getAddress().city();

        assertEquals(city1, city2);
        // --8<-- [end:faker_config_three_java]
    }

    @DisplayName("should be able to configure locale")
    @Test
    void configureLocale() {
        // --8<-- [start:faker_config_five_java]
        FakerConfig config = FakerConfig.builder()
            .withLocale("nb-NO")
            .build();
        Faker faker = new Faker(config);
        assertEquals(faker.getAddress().defaultCountry(), "Norge");
        // --8<-- [end:faker_config_five_java]
    }
}

