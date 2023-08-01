package com.greentree.engine.moon.assets.serializator;

import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.ConstAsset;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.ResultAssetKey;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;

public final class ResultAssetSerializator<T> extends TypedAssetSerializator<T> {

    public ResultAssetSerializator(TypeInfo<T> type) {
        super(type);
    }

    @Override
    public boolean canLoad(AssetManager manager, AssetKey key) {
        if (key instanceof ResultAssetKey res) {
            final var type = getType();
            return type.isInstance(res.result());
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Asset<T> load(AssetManager context, AssetKey key) {
        if (key instanceof ResultAssetKey res) {
            return new ConstAsset<>((T) res.result());
        }
        return null;
    }

}
