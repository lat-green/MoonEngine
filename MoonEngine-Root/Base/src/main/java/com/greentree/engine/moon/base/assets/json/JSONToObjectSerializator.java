package com.greentree.engine.moon.base.assets.json;

import com.google.gson.JsonElement;
import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value1Function;

@Deprecated
public final class JSONToObjectSerializator<T> implements AssetSerializator<T> {

    public final JSONFunction<T> INSTANCE;

    public JSONToObjectSerializator(TypeInfo<T> type) {
        INSTANCE = new JSONFunction<>(type);
    }

    @Override
    public TypeInfo<T> getType() {
        return INSTANCE.type;
    }

    @Override
    public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
        return manager.canLoad(JsonElement.class, key);
    }

    @Override
    public Value<T> load(LoadContext context, AssetKey ckey) {
        {
            final var json = context.load(JsonElement.class, ckey);
            if (json != null)
                return context.map(json, INSTANCE);
        }
        return null;
    }

    private static final class JSONFunction<T> implements Value1Function<JsonElement, T> {

        private static final long serialVersionUID = 1L;
        private final TypeInfo<T> type;

        public JSONFunction(TypeInfo<T> type) {
            this.type = type;
        }

        @Override
        public T apply(JsonElement res) {
            return JSONAssetSerializator.GSON.fromJson(res, type.getType());
        }

    }

}
