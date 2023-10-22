package com.greentree.engine.moon.base.assets.scene.adapters;

import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.xml.XMLElement;

public record MergeContext(Context a, Context b) implements Context {

    @Override
    public <T> Constructor<T> newInstance(TypeInfo<T> type, XMLElement element) {
        try {
            var constructor = a.newInstance(type, element);
            if (constructor != null)
                return constructor;
        } catch (Exception e1) {
            try {
                return b.newInstance(type, element);
            } catch (Exception e2) {
                e1.addSuppressed(e2);
                throw e1;
            }
        }
        return b.newInstance(type, element);
    }

}
