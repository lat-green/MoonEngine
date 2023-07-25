package com.greentree.engine.moon.assets.value.merge;

import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.provider.ValueProvider;

public final class M3Value<T1, T2, T3> implements Value<Group3<T1, T2, T3>> {

    private static final long serialVersionUID = 1L;

    private final Value<T1> Value1;
    private final Value<T2> Value2;
    private final Value<T3> Value3;

    public M3Value(Value<T1> Value1, Value<T2> Value2, Value<T3> Value3) {
        this.Value1 = Value1;
        this.Value2 = Value2;
        this.Value3 = Value3;
    }

    @Override
    public int characteristics() {
        return M2Provider.CHARACTERISTICS;
    }

    @Override
    public ValueProvider<Group3<T1, T2, T3>> openProvider() {
        return new M3Provider<>(Value1, Value2, Value3);
    }

}
