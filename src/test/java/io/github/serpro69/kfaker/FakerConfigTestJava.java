package io.github.serpro69.kfaker;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static io.github.serpro69.kfaker.FunctionalUtil.fromConsumer;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class FakerConfigTestJava {

    @Test
    void testConfiguringFakerInJava() {
        FakerConfig fakerConfig = FakerConfigBuilder.create(FakerConfig.builder(), fromConsumer(builder -> {
            builder.setLocale("en-AU");
            builder.setRandom(new Random(42));
        }));

        String name = new Faker(fakerConfig).getName().name();

        assertNotEquals("", name);
    }
}
