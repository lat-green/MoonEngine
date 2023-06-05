package com.greentree.engine.moon.assets.value.provider;

import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value1Function;

public final class ValueFunctionMapProvider<IN, OUT> extends AbstractMapProvider<IN, OUT> {

    public static final int CHARACTERISTICS = 0;
    private final Value1Function<? super IN, OUT> function;

    private ValueFunctionMapProvider(ValueProvider<IN> provider, Value1Function<? super IN, OUT> function) {
        super(provider);
        this.function = function;
        System.out.println(provider);
    }

    public static <T, R> ValueProvider<R> newProvider(Value<T> value, Value1Function<? super T, R> function) {
        return newProvider(value.openProvider(), function);
    }

    public static <T, R> ValueProvider<R> newProvider(ValueProvider<T> provider,
                                                      Value1Function<? super T, R> function) {
        if (provider.hasCharacteristics(CONST)) {
            final var v = provider.get();
            final var r = function.apply(v);
            return ConstProvider.newValue(r);
        }
        return new ValueFunctionMapProvider<>(provider, function);
    }

    @Override
    public String toString() {
        return "MapProvider (" + function.getClass().getSimpleName() + ")[" + input + "]";
    }

    @Override
    protected OUT map(IN in) {
        return function.apply(in);
    }

    @Override
    public ValueProvider<OUT> copy() {
        return newProvider(input, function);
    }

}
