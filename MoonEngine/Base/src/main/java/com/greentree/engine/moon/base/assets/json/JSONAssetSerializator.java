package com.greentree.engine.moon.base.assets.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.greentree.commons.data.resource.Resource;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

@Deprecated
public class JSONAssetSerializator implements AssetSerializator<JsonElement> {

    public static final Gson GSON = new GsonBuilder().create();

    @Override
    public Asset<JsonElement> load(AssetManager context, AssetKey ckey) {
        if (ckey instanceof ObjectToJsonKey k) {
            final var obj = context.load(Object.class, k.object());
            if (obj != null)
                return AssetKt.map(obj, JSONToObjectFunction.INSTANCE);
        }
        {
            final var res = context.load(Resource.class, ckey);
            if (res != null)
                return AssetKt.map(res, JSONFunction.INSTANCE);
        }
        {
            final var text = context.load(CharSequence.class, ckey);
            if (text != null)
                return AssetKt.map(text, JSONTextFunction.INSTANCE);
        }
        return null;
    }

    private static final class JSONFunction implements Value1Function<Resource, JsonElement> {

        public static final JSONFunction INSTANCE = new JSONFunction();
        private static final long serialVersionUID = 1L;

        @Override
        public JsonElement apply(Resource res) {
            try (final var in = res.open(); final var reader = new InputStreamReader(in)) {
                return JsonParser.parseReader(reader);
            } catch (IOException e) {
                throw new IllegalArgumentException(res.toString(), e);
            }
        }

    }

    private static final class JSONTextFunction
            implements Value1Function<CharSequence, JsonElement> {

        public static final JSONTextFunction INSTANCE = new JSONTextFunction();
        private static final long serialVersionUID = 1L;

        @Override
        public JsonElement apply(CharSequence text) {
            try (final var reader = new StringReader(text.toString())) {
                return JsonParser.parseReader(reader);
            }
        }

    }

    private static final class JSONToObjectFunction implements Value1Function<Object, JsonElement> {

        public static final JSONToObjectFunction INSTANCE = new JSONToObjectFunction();
        private static final long serialVersionUID = 1L;

        @Override
        public JsonElement apply(Object obj) {
            return GSON.toJsonTree(obj);
        }

    }

}
