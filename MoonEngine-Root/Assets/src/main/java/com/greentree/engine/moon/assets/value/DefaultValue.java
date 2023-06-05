package com.greentree.engine.moon.assets.value;

import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.assets.value.provider.DefultProvider;
import com.greentree.engine.moon.assets.value.provider.ValueProvider;

public final class DefaultValue<T> implements Value<T> {

    private static final long serialVersionUID = 1L;
    private final Iterable<? extends Value<T>> values;

    private DefaultValue(Iterable<? extends Value<T>> values) {
        this.values = values;
    }

    @SafeVarargs
    public static <T> Value<T> newValue(Value<T>... values) {
        return newValue(IteratorUtil.iterable(values));
    }

    public static <T> Value<T> newValue(Iterable<? extends Value<T>> values) {
        for (var v : values)
            if (v.hasCharacteristics(CONST))
                return v;
            else
                break;
        if (IteratorUtil.isEmpty(values))
            return NullValue.instance();
        if (IteratorUtil.size(values) == 1)
            return values.iterator().next();
        return new DefaultValue<>(values);
    }

    @Override
    public int characteristics() {
        return DefultProvider.CHARACTERISTICS;
    }

    @Override
    public ValueProvider<T> openProvider() {
        return DefultProvider.newProvider(values);
    }

    @Override
    public String toString() {
        return "DefaultValue [" + values + ']';
    }

}
