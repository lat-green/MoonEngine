package com.greentree.engine.moon.assets.value.provider;

import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.assets.value.Value;

import java.util.ArrayList;
import java.util.List;

public final class DefultProvider<T> implements ValueProvider<T> {

    public static final int CHARACTERISTICS = 0;
    private final List<? extends ValueProvider<T>> providers;
    private int index;

    private DefultProvider(List<? extends ValueProvider<T>> providers) {
        this.providers = providers;
    }

    public static <T> ValueProvider<T> newProvider(Iterable<? extends Value<T>> values) {
        final var providers = new ArrayList<ValueProvider<T>>();
        for (var s : values)
            providers.add(s.openProvider());
        return newProviderFromProviders(providers);
    }

    public static <T> ValueProvider<T> newProviderFromProviders(Iterable<? extends ValueProvider<T>> providers) {
        for (var v : providers)
            if (v.hasCharacteristics(CONST))
                return v;
            else
                break;
        List<ValueProvider<T>> list = new ArrayList<>();
        for (var i : providers)
            list.add(i);
        list = list.stream().distinct().filter(s -> !(s.hasCharacteristics(CONST) && s.isNull())).toList();
        if (list.isEmpty())
            return NullProvider.instance();
        if (list.size() == 1)
            return list.get(0);
        return new DefultProvider<T>(list);
    }

    @Override
    public int characteristics() {
        return CHARACTERISTICS;
    }

    @Override
    public T get() {
        init();
        return providers.get(index).get();
    }

    private void init() {
        index = 0;
        while (index < providers.size()) {
            final var s = providers.get(index);
            if (!s.isNull())
                return;
            index++;
        }
    }

    @Override
    public ValueProvider<T> copy() {
        return newProviderFromProviders(IteratorUtil.map(providers, p -> p.copy()));
    }

    @Override
    public boolean isChenge() {
        for (int i = 0; i < index; i++)
            if (providers.get(index).isChenge())
                return true;
        return false;
    }

    @Override
    public String toString() {
        return "DefaultProvider [" + providers + ']';
    }

}
