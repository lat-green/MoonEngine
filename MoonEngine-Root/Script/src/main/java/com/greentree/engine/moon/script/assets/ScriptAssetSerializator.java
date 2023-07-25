package com.greentree.engine.moon.script.assets;

import com.greentree.commons.data.resource.Resource;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;
import com.greentree.engine.moon.script.javascript.JavaScriptScript;
import org.mozilla.javascript.WrappedException;

import java.io.IOException;

public final class ScriptAssetSerializator implements AssetSerializator<JavaScriptScript> {

    @Override
    public boolean canLoad(AssetManager context, AssetKey key) {
        return context.canLoad(Resource.class, key);
    }

    @Override
    public Asset<JavaScriptScript> load(AssetManager context, AssetKey key) {
        {
            if (context.canLoad(Resource.class, key)) {
                final var res = context.load(Resource.class, key);
                return AssetKt.map(res, ScriptFunction.INSTANCE);
            }
        }
        return null;
    }

    private static final class ScriptFunction
            implements Value1Function<Resource, JavaScriptScript> {

        public static final ScriptFunction INSTANCE = new ScriptFunction();
        private static final long serialVersionUID = 1L;

        @Override
        public JavaScriptScript applyWithDest(Resource resource, JavaScriptScript dest) {
            final String script;
            try (final var in = resource.open()) {
                script = new String(in.readAllBytes());
            } catch (IOException e) {
                throw new WrappedException(e);
            }
            dest.setScript(script);
            return dest;
        }

        @Override
        public JavaScriptScript apply(Resource resource) {
            final String script;
            try (final var in = resource.open()) {
                script = new String(in.readAllBytes());
            } catch (IOException e) {
                throw new WrappedException(e);
            }
            return new JavaScriptScript(script);
        }

    }

}
