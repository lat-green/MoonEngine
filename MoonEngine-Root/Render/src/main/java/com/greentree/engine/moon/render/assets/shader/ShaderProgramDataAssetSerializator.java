package com.greentree.engine.moon.render.assets.shader;

import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.ResourceAssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value2Function;
import com.greentree.engine.moon.assets.value.function.Value3Function;
import com.greentree.engine.moon.base.assets.text.PropertyAssetKey;
import com.greentree.engine.moon.render.shader.ShaderData;
import com.greentree.engine.moon.render.shader.ShaderLanguage;
import com.greentree.engine.moon.render.shader.ShaderProgramDataImpl;
import com.greentree.engine.moon.render.shader.ShaderType;

import java.util.Properties;

public class ShaderProgramDataAssetSerializator implements AssetSerializator<ShaderProgramDataImpl> {

    @Override
    public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
        return manager.canLoad(Properties.class, key);
    }

    @Override
    public Value<ShaderProgramDataImpl> load(LoadContext manager, AssetKey ckey) {
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
            if (manager.isDeepValid(ShaderData.class, geomKey)) {
                final var geom = manager.load(ShaderData.class, geomKey);
                return manager.map(vert, frag, geom, new f3());
            }
            return manager.map(vert, frag, new f2());
        }
        return null;
    }

    private static final class f2 implements Value2Function<ShaderData, ShaderData, ShaderProgramDataImpl> {

        private static final long serialVersionUID = 1L;

        @Override
        public ShaderProgramDataImpl apply(ShaderData vert, ShaderData frag) {
            return new ShaderProgramDataImpl(vert, frag);
        }

    }

    private static final class f3 implements Value3Function<ShaderData, ShaderData, ShaderData, ShaderProgramDataImpl> {

        private static final long serialVersionUID = 1L;

        @Override
        public ShaderProgramDataImpl apply(ShaderData vert, ShaderData frag, ShaderData geom) {
            return new ShaderProgramDataImpl(vert, frag, geom);
        }

    }

}
