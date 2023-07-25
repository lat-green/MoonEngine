package com.greentree.engine.moon.assets.serializator;

import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.reflection.info.TypeUtil;
import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;

public final class MultiAssetSerializator<T> implements AssetSerializator<T> {

    private final Iterable<AssetSerializator<T>> serializators;

    public MultiAssetSerializator(Iterable<AssetSerializator<T>> iterable) {
        this.serializators = iterable;
    }

    @SuppressWarnings("unchecked")
    @Override
    public TypeInfo<T> getType() {
        if (IteratorUtil.isEmpty(serializators))
            return null;
        final var type = TypeUtil.lca(IteratorUtil.map(serializators, AssetSerializator::getType));
        return (TypeInfo<T>) type;
    }

    @Override
    public boolean canLoad(AssetManager manager, AssetKey key) {
        return IteratorUtil.any(serializators, s -> s.canLoad(manager, key));
    }

    @Override
    public Asset<T> load(AssetManager manager, AssetKey key) {
        for (var serializator : serializators)
            if (serializator.canLoad(manager, key)) {
                final var v = serializator.load(manager, key);
                if (v == null)
                    throw new NullPointerException(
                            "serializator " + serializator + " load return null");
                return v;
            }
        throw new IllegalArgumentException(
                "no one serializator can not load " + key + " " + IteratorUtil.toString(serializators));
    }

    @Override
    public String toString() {
        if (IteratorUtil.size(serializators) == 1)
            return serializators.iterator().next().toString();
        return "MultiAssetSerializator " + IteratorUtil.toString(serializators);
    }

}
