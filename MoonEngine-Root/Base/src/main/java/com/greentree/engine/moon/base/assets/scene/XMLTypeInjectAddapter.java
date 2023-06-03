package com.greentree.engine.moon.base.assets.scene;

import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.reflection.info.TypeUtil;
import com.greentree.commons.xml.XMLElement;

public interface XMLTypeInjectAddapter<T> {

    boolean injectFields(Context context, TypeInfo<? extends T> type, T dest, XMLElement element);

    @SuppressWarnings("unchecked")
    default Class<T> getType() {
        return (Class<T>) TypeUtil.getFirstAtgument(getClass(), XMLTypeInjectAddapter.class).toClass();
    }

}
