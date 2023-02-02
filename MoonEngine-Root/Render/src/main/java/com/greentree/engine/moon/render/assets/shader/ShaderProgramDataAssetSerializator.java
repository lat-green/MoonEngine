package com.greentree.engine.moon.render.assets.shader;

import java.util.Properties;

import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.ResourceAssetKey;
import com.greentree.engine.moon.assets.serializator.AbstractIterableAssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.merge.MIValue;
import com.greentree.engine.moon.base.assets.text.PropertyAssetKey;
import com.greentree.engine.moon.render.shader.ShaderLanguage;
import com.greentree.engine.moon.render.shader.ShaderType;

public class ShaderProgramDataAssetSerializator<S> extends AbstractIterableAssetSerializator<S> {
	
	public ShaderProgramDataAssetSerializator(TypeInfo<S> type) {
		super(type);
	}
	
	protected ShaderProgramDataAssetSerializator() {
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(Properties.class, key);
	}
	
	@Override
	public Value<Iterable<S>> load(LoadContext manager, AssetKey ckey) {
		if(manager.canLoad(Properties.class, ckey)) {
			final var vertProp = new ResourceAssetKey(new PropertyAssetKey(ckey, "vert"));
			final var fragProp = new ResourceAssetKey(new PropertyAssetKey(ckey, "frag"));
			final var geomProp = new ResourceAssetKey(new PropertyAssetKey(ckey, "geom"));
			
			final var vertKey = new ShaderAssetKey(vertProp, ShaderType.VERTEX,
					ShaderLanguage.GLSL);
			final var fragKey = new ShaderAssetKey(fragProp, ShaderType.FRAGMENT,
					ShaderLanguage.GLSL);
			final var geomKey = new ShaderAssetKey(geomProp, ShaderType.GEOMETRY,
					ShaderLanguage.GLSL);
			
			final var vert = manager.load(ITER_TYPE, vertKey);
			final var frag = manager.load(ITER_TYPE, fragKey);
			
			if(manager.isDeepValid(ITER_TYPE, geomKey)) {
				final var geom = manager.load(ITER_TYPE, geomKey);
				return new MIValue<>(vert, frag, geom);
			}
			return new MIValue<>(vert, frag);
		}
		return null;
	}
	
}
