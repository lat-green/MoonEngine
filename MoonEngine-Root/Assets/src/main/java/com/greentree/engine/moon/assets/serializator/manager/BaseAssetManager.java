package com.greentree.engine.moon.assets.serializator.manager;

import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.ThrowAsset;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MapMessage;

import java.util.HashMap;
import java.util.function.Function;

public final class BaseAssetManager implements MutableAssetManager {

    private static final Logger LOG = LogManager.getLogger(BaseAssetManager.class);

    private final AssetSerializatorContainer container = new AssetSerializatorContainer();

    public <T> Asset<T> load(TypeInfo<T> type, AssetKey key) {
        final var info = getInfo(type);
        Asset<T> result;
        try {
            result = info.load(this, key);
        } catch (RuntimeException e) {
//            LOG.info(ASSETS, "asset loader throw. info: " + info, e);
//            return NotValidAsset.instance();
            return new ThrowAsset(e);
        }
        LOG.debug(ASSETS, () -> new MapMessage<>(new HashMap<>() {{
            put("result", result);
            put("type", type);
            put("key", key);
        }}));
        return result;
    }

    @Override
    public boolean canLoad(TypeInfo<?> type, AssetKey key) {
        final var info = getInfo(type);
        if (info.canLoad(this, key)) {
            Asset<?> result;
            try {
                result = info.load(this, key);
            } catch (RuntimeException e) {
                return false;
            }
            return result.isValid();
        }
        return false;
    }

    private <T> AssetSerializatorContainer.AssetSerializatorInfo<T> getInfo(TypeInfo<T> type) {
        return container.get(type);
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

}
