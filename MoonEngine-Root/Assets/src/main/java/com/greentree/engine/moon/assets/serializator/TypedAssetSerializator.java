package com.greentree.engine.moon.assets.serializator;

import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.reflection.info.TypeInfoBuilder;
import com.greentree.commons.reflection.info.TypeUtil;

public abstract class TypedAssetSerializator<T> implements AssetSerializator<T> {

    public final TypeInfo<T> TYPE;

    protected TypedAssetSerializator() {
        TYPE = TypeUtil.getFirstAtgument(TypeInfoBuilder.getTypeInfo(getClass()),
                TypedAssetSerializator.class);
    }

    public TypedAssetSerializator(Class<T> cls) {
        this(TypeInfoBuilder.getTypeInfo(cls));
    }

    public TypedAssetSerializator(TypeInfo<T> type) {
        TYPE = type;
    }

    @Override
    public TypeInfo<T> getType() {
        return TYPE;
    }

}
