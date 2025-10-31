package io.github.serpro69.kfaker;

import org.junit.jupiter.api.Test;

import kotlin.random.Random;

import static io.github.serpro69.kfaker.FunctionalUtil.fromConsumer;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class FakerConfigTestJava {

    @Test
    void testConfiguringFakerInJava() {
        FakerConfig fakerConfig = FakerConfigBuilder.fakerConfig(fromConsumer(builder -> {
            builder.setLocale("en-AU");
            builder.setRandom(Random.Default);
        }));

        String name = new Faker(fakerConfig).getName().name();

        assertNotEquals("", name);
    }
}
