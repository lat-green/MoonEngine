package com.greentree.engine.moon.base.assets.json;

import com.google.gson.JsonElement;
import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;

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
    public Asset<T> load(AssetManager manager, AssetKey ckey) {
        final var json = manager.load(JsonElement.class, ckey);
        return AssetKt.map(json, INSTANCE);
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
