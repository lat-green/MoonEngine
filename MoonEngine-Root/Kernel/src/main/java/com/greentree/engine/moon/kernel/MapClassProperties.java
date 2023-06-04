package com.greentree.engine.moon.kernel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public class MapClassProperties<P> implements ClassProperties<P> {

    private final Map<Class<? extends P>, P> map = new HashMap<>();

    @Override
    public void add(P property) {
        var cls = (Class<? extends P>) property.getClass();
        if (contains(cls))
            throw new IllegalArgumentException("add already contains property " + cls);
        map.put(cls, property);
    }

    @Override
    public <T extends P> T remove(Class<? extends T> propertyClass) {
        if (!contains(propertyClass))
            throw new IllegalArgumentException("remove not contains property " + propertyClass);
        return (T) map.remove(propertyClass);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public <T extends P> Optional<T> getProperty(Class<? extends T> cls) {
        return Optional.ofNullable((T) map.get(cls));
    }

    @Override
    public boolean contains(Class<? extends P> cls) {
        return map.containsKey(cls);
    }

    @Override
    public Iterator<P> iterator() {
        return map.values().iterator();
    }

}