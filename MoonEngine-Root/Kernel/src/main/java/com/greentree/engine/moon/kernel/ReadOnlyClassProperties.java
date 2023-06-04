package com.greentree.engine.moon.kernel;

import java.util.Optional;

public interface ReadOnlyClassProperties<P> extends Iterable<P> {

    default <T extends P> T get(Class<? extends T> cls) {
        Optional<T> property;
        try {
            property = getProperty(cls);
        } catch (Exception e) {
            throw new IllegalArgumentException("get property of class: $cls", e);
        }
        return property.orElseThrow(() -> new IllegalArgumentException("not found property of class: $cls"));
    }

    <T extends P> Optional<T> getProperty(Class<? extends T> cls);

    default boolean contains(Class<? extends P> cls) {
        return getProperty(cls).isPresent();
    }

}
