package com.greentree.engine.moon.assets.serializator;

import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.reflection.info.TypeUtil;
import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.AssetKeyType;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.serializator.manager.DeepValidAssetManagerBase;
import com.greentree.engine.moon.assets.serializator.manager.DefaultAssetManager;
import com.greentree.engine.moon.assets.serializator.manager.ValidAssetManagerBase;
import com.greentree.engine.moon.assets.value.Value;

public final class MultiAssetSerializator<T> implements AssetSerializator<T> {

    private final Iterable<AssetSerializator<T>> iterable;

    public MultiAssetSerializator(Iterable<AssetSerializator<T>> iterable) {
        this.iterable = iterable;
    }

    @SuppressWarnings("unchecked")
    @Override
    public TypeInfo<T> getType() {
        if (IteratorUtil.isEmpty(iterable))
            return null;
        final var type = TypeUtil.lca(IteratorUtil.map(iterable, AssetSerializator::getType));
        return (TypeInfo<T>) type;
    }

    @Override
    public boolean isDeepValid(DeepValidAssetManagerBase manager, AssetKey key) {
        return IteratorUtil.any(iterable, s -> s.isDeepValid(manager, key));
    }

    @Override
    public boolean isValid(ValidAssetManagerBase manager, AssetKey key) {
        return IteratorUtil.any(iterable, s -> s.isValid(manager, key));
    }

    @Override
    public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
        return IteratorUtil.any(iterable, s -> s.canLoad(manager, key));
    }

    @Override
    public Value<T> load(LoadContext context, AssetKey key) {
        for (var serializator : iterable)
            if (serializator.canLoad(context, key)) {
                final var v = serializator.load(context, key);
                if (v == null)
                    throw new NullPointerException(
                            "serializator " + serializator + " load return null");
                return v;
            }
        throw new IllegalArgumentException(
                "no one serializator can not load " + key + " " + IteratorUtil.toString(iterable));
    }

    @Override
    public T loadDefault(DefaultAssetManager manager, AssetKeyType type) {
        for (var serializer : iterable) {
            final var v = serializer.loadDefault(manager, type);
            if (v != null)
                return v;
        }
        return null;
    }

    @Override
    public String toString() {
        if (IteratorUtil.size(iterable) == 1)
            return iterable.iterator().next().toString();
        return "MultiAssetSerializator " + IteratorUtil.toString(iterable);
    }

}
