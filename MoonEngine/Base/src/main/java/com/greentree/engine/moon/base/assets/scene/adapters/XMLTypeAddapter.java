package com.greentree.engine.moon.base.assets.scene.adapters;

import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.reflection.info.TypeInfoBuilder;
import com.greentree.commons.xml.XMLElement;

public interface XMLTypeAddapter {

    default <T> Constructor<T> newInstance(Context context, Class<T> cls, XMLElement element) {
        final var type = TypeInfoBuilder.getTypeInfo(cls);
        return newInstance(context, type, element);
    }

    <T> Constructor<T> newInstance(Context context, TypeInfo<T> type, XMLElement element);

    default Class<?> getLoadOnly() {
        return null;
    }

}
