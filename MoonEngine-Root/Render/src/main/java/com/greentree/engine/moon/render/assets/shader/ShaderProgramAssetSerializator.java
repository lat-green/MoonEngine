package com.greentree.engine.moon.render.assets.shader;

import java.util.Properties;

import com.greentree.common.renderer.shader.ShaderType;
import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.ResourceAssetKey;
import com.greentree.commons.assets.serializator.AbstractIterableAssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.merge.MIValue;
import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.engine.moon.base.assets.text.PropertyAssetKey;

public class ShaderProgramAssetSerializator<S> extends AbstractIterableAssetSerializator<S> {
	
	public ShaderProgramAssetSerializator(TypeInfo<S> type) {
		super(type);
	}
	
	protected ShaderProgramAssetSerializator() {
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
			
			final var vertKey = new ShaderAssetKey(vertProp, ShaderType.VERTEX);
			final var fragKey = new ShaderAssetKey(fragProp, ShaderType.FRAGMENT);
			final var geomKey = new ShaderAssetKey(geomProp, ShaderType.GEOMETRY);
			
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
