package com.greentree.engine.moon.assets.serializator.manager;

import com.greentree.commons.data.resource.location.ResourceLocation;
import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.AssetKeyType;
import com.greentree.engine.moon.assets.location.AssetLocation;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.value.CacheProviderValue;
import com.greentree.engine.moon.assets.value.CecheValue;
import com.greentree.engine.moon.assets.value.Value;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.message.MapMessage;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

public final class AssetManager implements AssetManagerBase, ValidAssetManagerBase, DeepValidAssetManagerBase {

    private static final Logger LOG = LogManager.getLogger(AssetManager.class);
    private static final Marker ASSETS = MarkerManager.getMarker("assets");

    private final AssetSerializatorContainer container = new AssetSerializatorContainer();
    private final ExecutorService executor;

    public AssetManager() {
        this(Executors.newFixedThreadPool(4, r -> {
            final var thread = new Thread(r, "AssetManager");
            thread.setDaemon(true);
            thread.setPriority(Thread.MAX_PRIORITY);
            return thread;
        }));
    }

    public AssetManager(ExecutorService executor) {
        this.executor = executor;
    }

    public void addAssetLocation(AssetLocation location) {
        container.addAssetLocation(location);
    }

    public void addGenerator(
            Function<? super TypeInfo<?>, ? extends AssetSerializator<?>> generator) {
        container.addGenerator(generator);
    }

    public void addResourceLocation(ResourceLocation location) {
        container.addResourceLocation(location);
    }

    public <T> void addSerializator(AssetSerializator<T> serializator) {
        container.addSerializator(serializator);
    }

    @Override
    public boolean isDeepValid(TypeInfo<?> type, AssetKey key) {
        final var info = getInfo(type);
        return info.isDeepValid(this, key);
    }

    private <T> AssetSerializatorContainer.AssetSerializatorInfo<T> getInfo(TypeInfo<T> type) {
        return container.get(type);
    }

    @Override
    public boolean isValid(TypeInfo<?> type, AssetKey key) {
        final var info = getInfo(type);
        return info.isValid(this, key);
    }

    @Override
    public boolean canLoad(TypeInfo<?> type, AssetKey key) {
        final var info = getInfo(type);
        return info.canLoad(this, key);
    }

    @Override
    public <T> Value<T> load(TypeInfo<T> type, AssetKey key) {
        final var wrapper = new BaseLoadContext();
        final var result = wrapper.load(type, key);
        return result;
    }

    @Override
    public <T> T loadDefault(TypeInfo<T> type, AssetKeyType asset_type) {
        final var info = getInfo(type);
        return info.loadDefault(this, asset_type);
    }

    private abstract class AbstractLoadContext implements LoadContext {

        @Override
        public <T> Value<T> load(TypeInfo<T> type, AssetKey key) {
            final var result = tryLoadAsync(type, key);
            return CacheProviderValue.newValue(CecheValue.newValue(result));
        }

        protected <T> Value<T> tryLoadAsync(TypeInfo<T> type, AssetKey key) {
            return loadForward(type, key);
        }

        protected <T> Value<T> loadForward(TypeInfo<T> type, AssetKey key) {
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
        public boolean isDeepValid(TypeInfo<?> type, AssetKey key) {
            return AssetManager.this.isDeepValid(type, key);
        }

        @Override
        public boolean isValid(TypeInfo<?> type, AssetKey key) {
            return AssetManager.this.isValid(type, key);
        }

        @Override
        public boolean canLoad(TypeInfo<?> type, AssetKey key) {
            return AssetManager.this.canLoad(type, key);
        }

        @Override
        public <T> T loadDefault(TypeInfo<T> type, AssetKeyType asset_type) {
            return AssetManager.this.loadDefault(type, asset_type);
        }

    }

    private final class BaseLoadContext extends AbstractLoadContext {

    }

}
