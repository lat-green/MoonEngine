package com.greentree.engine.moon.kernel;

public interface ClassProperties<P> extends ReadOnlyClassProperties<P> {

    void add(P property);

    <T extends P> T remove(Class<? extends T> propertyClass);

    void clear();

}