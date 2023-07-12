package com.greentree.engine.moon.assets.value;

import com.greentree.engine.moon.assets.value.function.Value1Function;
import com.greentree.engine.moon.assets.value.provider.ValueFunctionMapProvider;
import com.greentree.engine.moon.assets.value.provider.ValueProvider;

public final class ValueFunctionMapValue<T, R> implements Value<R> {

    private static final long serialVersionUID = 1L;
    private static final int PLUS = ONE_PROVIDER | CONST;
    private final Value<T> value;
    private final Value1Function<? super T, R> function;

    private ValueFunctionMapValue(Value<T> value, Value1Function<? super T, R> function) {
        this.value = value;
        this.function = function;
    }

    public static <T, R> Value<R> newValue(Value<T> value, Value1Function<? super T, R> function) {
        if (value.hasCharacteristics(CONST)) {
            final var p = value.openProvider();
            final var v = p.get();
            final var r = function.apply(v);
            return ConstValue.newValue(r);
        }
        return new ValueFunctionMapValue<>(value, function);
    }

    @Override
    public String toString() {
        return "MapValue  (" + function.getClass().getSimpleName() + ")[" + value + "]";
    }

    @Override
    public ValueProvider<R> openProvider() {
        return ValueFunctionMapProvider.newProvider(value, function);
    }

    @Override
    public int characteristics() {
        return ValueFunctionMapProvider.CHARACTERISTICS | ((PLUS & value.characteristics()));
    }

}
