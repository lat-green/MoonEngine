package com.greentree.engine.moon.assets.serializator.manager;

import com.greentree.commons.data.resource.location.ResourceLocation;
import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.reflection.info.TypeInfoBuilder;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.ResourceAssetKey;
import com.greentree.engine.moon.assets.key.ResultAssetKey;
import com.greentree.engine.moon.assets.location.AssetLocation;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.NamedAssetSerializator;
import com.greentree.engine.moon.assets.serializator.ResourceAssetSerializator;

import java.util.function.Function;

public interface AssetManager {

    default <T> Asset<T> load(Class<T> cls, Object key) {
        return load(cls, new ResultAssetKey(key));
    }

    default <T> Asset<T> load(Class<T> cls, AssetKey key) {
        final var type = TypeInfoBuilder.getTypeInfo(cls);
        return load(type, key);
    }

    <T> Asset<T> load(TypeInfo<T> type, AssetKey key);

    default <T> Asset<T> load(Class<T> cls, String resource) {
        return load(cls, new ResourceAssetKey(resource));
    }

    default <T> Asset<T> load(TypeInfo<T> type, Object key) {
        return load(type, new ResultAssetKey(key));
    }

    default <T> Asset<T> load(TypeInfo<T> type, String resource) {
        return load(type, new ResourceAssetKey(resource));
    }

    default boolean canLoad(Class<?> cls, AssetKey key) {
        final var type = TypeInfoBuilder.getTypeInfo(cls);
        return canLoad(type, key);
    }

    boolean canLoad(TypeInfo<?> type, AssetKey key);

    default void addResourceLocation(ResourceLocation location) {
        addSerializator(new ResourceAssetSerializator(location));
    }

    <T> void addSerializator(AssetSerializator<T> serializator);

    default void addAssetLocation(AssetLocation location) {
        addGenerator(t -> new NamedAssetSerializator<>(location, t));
    }

    void addGenerator(Function<? super TypeInfo<?>, ? extends AssetSerializator<?>> generator);

}
