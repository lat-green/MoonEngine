package com.greentree.engine.moon.assets.value.merge;

import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.provider.ValueProvider;

public final class M5Provider<T1, T2, T3, T4, T5> implements ValueProvider<Group5<T1, T2, T3, T4, T5>> {

    public static final int CHARACTERISTICS = NOT_NULL | CECHED;
    private final ValueProvider<T1> provider1;
    private final ValueProvider<T2> provider2;
    private final ValueProvider<T3> provider3;
    private final ValueProvider<T4> provider4;
    private final ValueProvider<T5> provider5;

    public M5Provider(Value<T1> Value1, Value<T2> Value2, Value<T3> Value3, Value<T4> Value4, Value<T5> Value5) {
        this.provider1 = Value1.openProvider();
        this.provider2 = Value2.openProvider();
        this.provider3 = Value3.openProvider();
        this.provider4 = Value4.openProvider();
        this.provider5 = Value5.openProvider();
    }

    public M5Provider(ValueProvider<T1> provider1, ValueProvider<T2> provider2, ValueProvider<T3> provider3,
                      ValueProvider<T4> provider4, ValueProvider<T5> provider5) {
        this.provider1 = provider1;
        this.provider2 = provider2;
        this.provider3 = provider3;
        this.provider4 = provider4;
        this.provider5 = provider5;
    }

    @Override
    public int characteristics() {
        return CHARACTERISTICS | (provider1.characteristics() & provider2.characteristics() & provider3.characteristics() & provider4.characteristics() & provider5.characteristics());
    }

    @Override
    public Group5<T1, T2, T3, T4, T5> get() {
        final var v1 = provider1.get();
        final var v2 = provider2.get();
        final var v3 = provider3.get();
        final var v4 = provider4.get();
        final var v5 = provider5.get();
        return new Group5<>(v1, v2, v3, v4, v5);
    }

    @Override
    public ValueProvider<Group5<T1, T2, T3, T4, T5>> copy() {
        return new M5Provider<>(provider1.copy(), provider2.copy(), provider3.copy(), provider4.copy(),
                provider5.copy());
    }

    @Override
    public boolean isChenge() {
        return provider1.isChenge() || provider2.isChenge() || provider3.isChenge() || provider4.isChenge()
                || provider5.isChenge();
    }

    @Override
    public String toString() {
        return "[" + provider1 + ", " + provider2 + ", " + provider3 + ", " + provider4 + ", " + provider5 + "]";
    }

}
