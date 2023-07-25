package com.greentree.engine.moon.assets.serializator.manager;

import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.message.MapMessage;

import java.util.HashMap;
import java.util.function.Function;

public final class BaseAssetManager implements AssetManager {

    private static final Logger LOG = LogManager.getLogger(BaseAssetManager.class);
    private static final Marker ASSETS = MarkerManager.getMarker("assets");

    private final AssetSerializatorContainer container = new AssetSerializatorContainer();

    public <T> Asset<T> load(TypeInfo<T> type, AssetKey key) {
        final var info = getInfo(type);
        try {
            final var result = info.load(this, key);
            LOG.debug(ASSETS, () -> new MapMessage<>(new HashMap<>() {{
                put("result", result);
                put("type", type);
                put("key", key);
            }}));
            return result;
        } catch (Exception e) {
            LOG.warn(ASSETS, "asset loader throw. info: " + info, e);
            throw e;
        }
    }

    @Override
    public boolean canLoad(TypeInfo<?> type, AssetKey key) {
        final var info = getInfo(type);
        return info.canLoad(this, key);
    }

    @Override
    public <T> void addSerializator(AssetSerializator<T> serializator) {
        container.addSerializator(serializator);
    }

    @Override
    public void addGenerator(
            Function<? super TypeInfo<?>, ? extends AssetSerializator<?>> generator) {
        container.addGenerator(generator);
    }

    private <T> AssetSerializatorContainer.AssetSerializatorInfo<T> getInfo(TypeInfo<T> type) {
        return container.get(type);
    }

}
