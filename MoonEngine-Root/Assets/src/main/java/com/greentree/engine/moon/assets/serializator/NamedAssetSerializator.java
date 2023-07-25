package com.greentree.engine.moon.assets.serializator;

import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.NamedAssetKey;
import com.greentree.engine.moon.assets.location.AssetLocation;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;

public class NamedAssetSerializator<T> implements AssetSerializator<T> {

    private final AssetLocation location;
    private final TypeInfo<T> TYPE;

    public NamedAssetSerializator(AssetLocation location, TypeInfo<T> TYPE) {
        this.location = location;
        this.TYPE = TYPE;
    }

    @Override
    public boolean canLoad(AssetManager manager, AssetKey ckey) {
        if (ckey instanceof NamedAssetKey key) {
            final var real_key = location.getKey(key.name());
            return manager.canLoad(TYPE, real_key);
        }
        return false;
    }

    @Override
    public Asset<T> load(AssetManager manager, AssetKey ckey) {
        if (ckey instanceof NamedAssetKey key) {
            final var real_key = location.getKey(key.name());
            return manager.load(TYPE, real_key);
        }
        return null;
    }

}
