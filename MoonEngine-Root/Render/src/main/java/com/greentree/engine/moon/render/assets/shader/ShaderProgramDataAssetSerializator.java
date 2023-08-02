package com.greentree.engine.moon.render.assets.shader;

import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value2Function;
import com.greentree.engine.moon.assets.asset.Value3Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.ResourceAssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;
import com.greentree.engine.moon.base.assets.text.PropertyAssetKey;
import com.greentree.engine.moon.render.shader.ShaderData;
import com.greentree.engine.moon.render.shader.ShaderLanguage;
import com.greentree.engine.moon.render.shader.ShaderProgramDataImpl;
import com.greentree.engine.moon.render.shader.ShaderType;

import java.util.Properties;

public class ShaderProgramDataAssetSerializator implements AssetSerializator<ShaderProgramDataImpl> {

    @Override
    public boolean canLoad(AssetManager manager, AssetKey key) {
        return manager.canLoad(Properties.class, key);
    }

    @Override
    public Asset<ShaderProgramDataImpl> load(AssetManager manager, AssetKey ckey) {
        if (manager.canLoad(Properties.class, ckey)) {
            final var vertProp = new ResourceAssetKey(new PropertyAssetKey(ckey, "vert"));
            final var fragProp = new ResourceAssetKey(new PropertyAssetKey(ckey, "frag"));
            final var geomProp = new ResourceAssetKey(new PropertyAssetKey(ckey, "geom"));
            final var vertKey = new ShaderAssetKey(vertProp, ShaderType.VERTEX,
                    ShaderLanguage.GLSL);
            final var fragKey = new ShaderAssetKey(fragProp, ShaderType.FRAGMENT,
                    ShaderLanguage.GLSL);
            final var geomKey = new ShaderAssetKey(geomProp, ShaderType.GEOMETRY,
                    ShaderLanguage.GLSL);
            final var vert = manager.load(ShaderData.class, vertKey);
            final var frag = manager.load(ShaderData.class, fragKey);
            if (manager.canLoad(ShaderData.class, geomKey)) {
                final var geom = manager.load(ShaderData.class, geomKey);
                return AssetKt.map(vert, frag, geom, new VertFragGeomShader());
            }
            return AssetKt.map(vert, frag, new VertFragShader());
        }
        return null;
    }

    private static final class VertFragShader implements Value2Function<ShaderData, ShaderData, ShaderProgramDataImpl> {

        private static final long serialVersionUID = 1L;

        @Override
        public String toString() {
            return "VertFragShader";
        }

        @Override
        public ShaderProgramDataImpl apply(ShaderData vert, ShaderData frag) {
            return new ShaderProgramDataImpl(vert, frag);
        }

    }

    private static final class VertFragGeomShader implements Value3Function<ShaderData, ShaderData, ShaderData, ShaderProgramDataImpl> {

        private static final long serialVersionUID = 1L;

        @Override
        public String toString() {
            return "VertFragGeomShader";
        }

        @Override
        public ShaderProgramDataImpl apply(ShaderData vert, ShaderData frag, ShaderData geom) {
            return new ShaderProgramDataImpl(vert, frag, geom);
        }

    }

}
