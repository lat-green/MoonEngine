package com.greentree.engine.moon.assets.serializator.manager;

import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.ThrowAsset;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.cache.WeakHashMapCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MapMessage;

import java.util.HashMap;
import java.util.function.Function;

public final class BaseAssetManager implements MutableAssetManager {

    private static final Logger LOG = LogManager.getLogger(BaseAssetManager.class);

    //    private final AssetSerializatorContainer container = new AssetSerializatorContainer(HashMapCache::new);
    private final AssetSerializatorContainer container = new AssetSerializatorContainer(WeakHashMapCache::new);

    public <T> Asset<T> load(TypeInfo<T> type, AssetKey key) {
        Asset<T> result;
        try {
            result = container.load(this, type, key);
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
    public <T> void addSerializator(AssetSerializator<T> serializator) {
        container.addSerializator(serializator);
    }

    @Override
    public void addGenerator(
            Function<? super TypeInfo<?>, ? extends AssetSerializator<?>> generator) {
        container.addGenerator(generator);
    }

}
