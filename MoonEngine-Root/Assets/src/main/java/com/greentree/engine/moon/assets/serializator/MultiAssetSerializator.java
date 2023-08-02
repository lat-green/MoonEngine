package com.greentree.engine.moon.assets.serializator;

import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.reflection.info.TypeUtil;
import com.greentree.commons.util.exception.MultiException;
import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;

import java.util.ArrayList;

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
        var exceptions = new ArrayList<RuntimeException>();
        for (var serializator : serializators)
            if (serializator.canLoad(manager, key)) {
                final Asset<T> result;
                try {
                    result = serializator.load(manager, key);
                } catch (RuntimeException e) {
                    exceptions.add(e);
                    continue;
                }
                if (result == null)
                    throw new NullPointerException(
                            "serializator " + serializator + " load return null");
                return result;
            }
        if (exceptions.isEmpty())
            throw new IllegalArgumentException(
                    "no one serializator can not load " + key + " " + IteratorUtil.toString(serializators));
        throw new MultiException(exceptions);
    }

    @Override
    public String toString() {
        if (IteratorUtil.size(serializators) == 1)
            return serializators.iterator().next().toString();
        return "MultiAssetSerializator " + IteratorUtil.toString(serializators);
    }

}
