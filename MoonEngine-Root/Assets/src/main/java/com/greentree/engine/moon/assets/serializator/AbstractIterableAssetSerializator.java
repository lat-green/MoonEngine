package com.greentree.engine.moon.assets.serializator;

import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.reflection.info.TypeInfoBuilder;
import com.greentree.commons.reflection.info.TypeUtil;

public abstract class AbstractIterableAssetSerializator<T>
        implements AssetSerializator<Iterable<T>> {

    public final TypeInfo<T> ITER_TYPE;

    public final TypeInfo<Iterable<T>> TYPE;

    public AbstractIterableAssetSerializator(Class<T> cls) {
        this(TypeInfoBuilder.getTypeInfo(cls));
    }

    public AbstractIterableAssetSerializator(TypeInfo<T> type) {
        ITER_TYPE = type;
        TYPE = TypeInfoBuilder.getTypeInfo(Iterable.class, ITER_TYPE);
    }

    protected AbstractIterableAssetSerializator() {
        ITER_TYPE = TypeUtil.getFirstAtgument(TypeInfoBuilder.getTypeInfo(getClass()),
                AbstractIterableAssetSerializator.class);
        TYPE = TypeInfoBuilder.getTypeInfo(Iterable.class, ITER_TYPE);
    }

    @Override
    public final TypeInfo<Iterable<T>> getType() {
        return TYPE;
    }

}
