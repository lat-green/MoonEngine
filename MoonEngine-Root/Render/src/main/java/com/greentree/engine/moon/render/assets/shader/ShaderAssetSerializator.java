package com.greentree.engine.moon.render.assets.shader;

import com.greentree.common.renderer.shader.ShaderType;
import com.greentree.common.renderer.shader.data.ShaderDataImpl;
import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.serializator.manager.ValidAssetManagerBase;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value1Function;


public class ShaderAssetSerializator implements AssetSerializator<ShaderDataImpl> {
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey ckey) {
		if(ckey instanceof ShaderAssetKey key) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isValid(ValidAssetManagerBase manager, AssetKey ckey) {
		if(ckey instanceof ShaderAssetKey key) {
			return manager.isValid(String.class, key.text());
		}
		return false;
	}
	
	@Override
	public Value<ShaderDataImpl> load(LoadContext manager, AssetKey ckey) {
		if(ckey instanceof ShaderAssetKey key) {
			final var text = manager.load(String.class, key.text());
			final var type = key.shaderType();
			return manager.map(text, new ShaderDataImplFunction(type));
		}
		return null;
	}
	
	private static final class ShaderDataImplFunction
			implements Value1Function<String, ShaderDataImpl> {
		
		private static final long serialVersionUID = 1L;
		private final ShaderType type;
		
		public ShaderDataImplFunction(ShaderType type) {
			this.type = type;
		}
		
		@Override
		public ShaderDataImpl apply(String value) {
			if(value == null)
				return null;
			return new ShaderDataImpl(value, type);
		}
		
	}
	
}
