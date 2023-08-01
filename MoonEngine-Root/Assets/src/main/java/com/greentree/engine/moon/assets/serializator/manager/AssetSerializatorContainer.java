package com.greentree.engine.moon.assets.serializator.manager;

import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.reflection.info.TypeUtil;
import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.CacheAsset;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.*;
import com.greentree.engine.moon.assets.serializator.manager.cache.Cache;
import com.greentree.engine.moon.assets.serializator.manager.cache.WeakHashMapCache;

import java.util.*;
import java.util.function.Function;

final class AssetSerializatorContainer {

    private final Map<TypeInfo<?>, AssetSerializatorInfo<?>> serializators = new HashMap<>();

    private final Collection<Function<? super TypeInfo<?>, ? extends AssetSerializator<?>>> generators = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public AssetSerializatorContainer() {
        addGenerator(DefaultSerializator::new);
        addGenerator(ResultAssetSerializator::new);
    }

    public void addGenerator(
            Function<? super TypeInfo<?>, ? extends AssetSerializator<?>> generator) {
        generators.add(generator);
        synchronized (serializators) {
            for (var t : serializators.keySet())
                runGenerator(t, generator);
        }
    }

    public <T> void addSerializator(AssetSerializator<T> serializator) {
        final var type = serializator.getType();
        final var info = get(type);
        info.addSerializator(serializator);
    }

    public <T> void addGeneratedSerializator(AssetSerializator<T> serializator) {
        final var type = serializator.getType();
        final var info = get(type);
        info.addGeneratedSerializator(serializator);
    }

    @SuppressWarnings("unchecked")
    public <T> AssetSerializatorInfo<T> get(TypeInfo<T> type) {
        synchronized (serializators) {
            if (!serializators.containsKey(type)) {
                final var info = new AssetSerializatorInfo<>(type);
                for (var s : serializators.keySet()) {
                    if (TypeUtil.isExtends(s, type)) {
                        final var ss = (AssetSerializatorInfo<T>) serializators.get(s);
                        ss.serializatorInfos.add(info);
                    }
                    if (TypeUtil.isExtends(type, s)) {
                        final var ss = (AssetSerializatorInfo<T>) serializators.get(s);
                        info.serializatorInfos.add(ss);
                    }
                }
                serializators.put(type, info);
                onNewType(type);
                return info;
            }
            return (AssetSerializatorInfo<T>) serializators.get(type);
        }
    }

    private void onNewType(TypeInfo<?> type) {
        for (var g : generators)
            runGenerator(type, g);
    }

    private void runGenerator(TypeInfo<?> type,
                              Function<? super TypeInfo<?>, ? extends AssetSerializator<?>> generator) {
        final var s = generator.apply(type);
        if (s != null)
            addGeneratedSerializator(s);
    }

    public final class AssetSerializatorInfo<T> extends TypedAssetSerializator<T> {

        private final List<AssetSerializatorInfo<T>> serializatorInfos = new ArrayList<>();
        private final List<AssetSerializator<T>> generatedSerializators = new ArrayList<>();
        private final List<AssetSerializator<T>> serializators = new ArrayList<>();
        private final AssetSerializator<T> serializator = new MultiAssetSerializator<>(
                IteratorUtil.union(generatedSerializators, serializators, serializatorInfos));

        //                private final Cache<AssetKey, Asset<T>> cache = new HashMapCache<>();
        private final Cache<AssetKey, Asset<T>> cache = new WeakHashMapCache<>();
//        private final Cache<AssetKey, Asset<T>> cache = new DirectoryCache<>(Assets.getTempCacheDirectory(TYPE));

        public AssetSerializatorInfo(TypeInfo<T> type) {
            super(type);
        }

        public void addGeneratedSerializator(AssetSerializator<T> serializator) {
            generatedSerializators.add(serializator);
        }

        public void addSerializator(AssetSerializator<T> serializator) {
            serializators.add(serializator);
        }

        @Override
        public boolean canLoad(AssetManager manager, AssetKey key) {
            if (cache.has(key))
                return true;
            return serializator.canLoad(manager, key);
        }

        @Override
        public Asset<T> load(AssetManager manager, AssetKey key) {
            final var v = cache.set(key, () -> {
                try {
                    var value = serializator.load(manager, key);
                    value = CacheAsset.Companion.newAsset(value);
                    return value;
                } catch (Exception e) {
                    throw new IllegalArgumentException("type:" + getType() + " key:" + key, e);
                }
            });
            return v;
        }

        @Override
        public String toString() {
            var builder = new StringBuilder();
            builder.append("AssetSerializatorInfo [");
            builder.append("type=");
            builder.append(getType());
            if (!serializators.isEmpty()) {
                builder.append(", ");
                builder.append(serializators);
            }
            if (!serializatorInfos.isEmpty()) {
                builder.append(", ");
                var joiner = new StringJoiner(", ");
                for (var s : serializatorInfos)
                    joiner.add(s.toString());
                builder.append(joiner);
            }
            builder.append(']');
            return builder.toString();
        }

    }

}
