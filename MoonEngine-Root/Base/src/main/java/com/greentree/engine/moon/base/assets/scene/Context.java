package com.greentree.engine.moon.base.assets.scene;

import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.reflection.info.TypeInfoBuilder;
import com.greentree.commons.xml.XMLElement;

public interface Context {

    default <T> T build(Class<T> cls, XMLElement element) {
        final var type = TypeInfoBuilder.getTypeInfo(cls);
        return build(type, element);
    }

    default <T> T build(TypeInfo<T> type, XMLElement element) {
        try (final var c = newInstance(type, element)) {
            if (c == null)
                throw new NullPointerException("type:" + type + " element:" + element);
            return c.value();
        }
    }

    <T> Constructor<T> newInstance(TypeInfo<T> type, XMLElement element);

    default <T> Constructor<T> newInstance(Class<T> cls, XMLElement element) {
        final var type = TypeInfoBuilder.getTypeInfo(cls);
        return newInstance(type, element);
    }

}