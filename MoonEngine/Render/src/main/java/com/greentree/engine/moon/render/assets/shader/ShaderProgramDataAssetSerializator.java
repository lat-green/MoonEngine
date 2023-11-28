package com.greentree.engine.moon.render.assets.shader;

import com.greentree.engine.moon.assets.Value2Function;
import com.greentree.engine.moon.assets.Value3Function;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.ResourceAssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader;
import com.greentree.engine.moon.base.assets.text.PropertyAssetKey;
import com.greentree.engine.moon.render.shader.ShaderData;
import com.greentree.engine.moon.render.shader.ShaderLanguage;
import com.greentree.engine.moon.render.shader.ShaderProgramDataImpl;
import com.greentree.engine.moon.render.shader.ShaderType;

public class ShaderProgramDataAssetSerializator implements AssetSerializator<ShaderProgramDataImpl> {

    @Override
    public Asset<ShaderProgramDataImpl> load(AssetLoader.Context manager, AssetKey ckey) {
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
        final var geom = manager.load(ShaderData.class, geomKey);
        if (geom.isValid()) {
            return AssetKt.map(vert, frag, geom, new VertFragGeomShader());
        }
        return AssetKt.map(vert, frag, new VertFragShader());
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
