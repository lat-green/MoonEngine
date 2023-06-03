package com.greentree.engine.moon.assets.serializator.request;

import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.engine.moon.assets.key.AssetKeyType;

public interface DefaultLoadRequest<T> {

    TypeInfo<T> loadType();

    AssetKeyType type();

}
