package com.greentree.engine.moon.assets.serializator;

import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.reflection.info.TypeUtil;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.AssetKeyType;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.serializator.manager.DeepValidAssetManagerBase;
import com.greentree.engine.moon.assets.serializator.manager.DefaultAssetManager;
import com.greentree.engine.moon.assets.serializator.manager.ValidAssetManagerBase;
import com.greentree.engine.moon.assets.value.Value;

public interface AssetSerializator<T> {

    default TypeInfo<T> getType() {
        return TypeUtil.getFirstAtgument(getClass(), AssetSerializator.class);
    }

    default boolean isDeepValid(DeepValidAssetManagerBase manager, AssetKey key) {
        return isValid((type, key1) -> manager.isDeepValid(type, key1), key);
    }

    default boolean isValid(ValidAssetManagerBase manager, AssetKey key) {
        if (manager instanceof CanLoadAssetManager c)
            return canLoad(c, key);
        return canLoad((type, key1) -> manager.isValid(type, key1), key);
    }

    boolean canLoad(CanLoadAssetManager manager, AssetKey key);

    Value<T> load(LoadContext context, AssetKey key);

    default T loadDefault(DefaultAssetManager manager, AssetKeyType type) {
        return null;
    }

}
