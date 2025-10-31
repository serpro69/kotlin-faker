package io.github.serpro69.kfaker;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

import java.util.function.Consumer;

public class FunctionalUtil {

    public static <T> Function1<T, Unit> fromConsumer(Consumer<T> callable) {
        return t -> {
            callable.accept(t);
            return Unit.INSTANCE;
        };
    }
}
