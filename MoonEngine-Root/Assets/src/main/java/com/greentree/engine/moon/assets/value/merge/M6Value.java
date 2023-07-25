package com.greentree.engine.moon.assets.value.merge;

import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.provider.ValueProvider;

public final class M6Value<T1, T2, T3, T4, T5, T6>
        implements Value<Group6<T1, T2, T3, T4, T5, T6>> {

    private static final long serialVersionUID = 1L;

    private final Value<T1> Value1;
    private final Value<T2> Value2;
    private final Value<T3> Value3;
    private final Value<T4> Value4;
    private final Value<T5> Value5;
    private final Value<T6> Value6;

    public M6Value(Value<T1> Value1, Value<T2> Value2, Value<T3> Value3, Value<T4> Value4,
                   Value<T5> Value5, Value<T6> Value6) {
        this.Value1 = Value1;
        this.Value2 = Value2;
        this.Value3 = Value3;
        this.Value4 = Value4;
        this.Value5 = Value5;
        this.Value6 = Value6;
    }

    @Override
    public int characteristics() {
        return M2Provider.CHARACTERISTICS;
    }

    @Override
    public ValueProvider<Group6<T1, T2, T3, T4, T5, T6>> openProvider() {
        return new M6Provider<>(Value1, Value2, Value3, Value4, Value5, Value6);
    }

}
