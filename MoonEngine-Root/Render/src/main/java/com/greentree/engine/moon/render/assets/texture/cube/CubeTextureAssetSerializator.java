package com.greentree.engine.moon.render.assets.texture.cube;

import java.util.Objects;

import com.greentree.common.renderer.texture.CubeImageData;
import com.greentree.common.renderer.texture.CubeTextureData;
import com.greentree.common.renderer.texture.Texture3DType;
import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.ResourceAssetKey;
import com.greentree.commons.assets.key.ResultAssetKey;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value1Function;

public class CubeTextureAssetSerializator implements AssetSerializator<CubeTextureData> {
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return key instanceof CubeTextureAssetKey || key instanceof ResourceAssetKey
				|| (key instanceof ResultAssetKey k && k.result() instanceof String);
	}
	
	public Value<CubeTextureData> load(LoadContext manager, CubeTextureAssetKey key) {
		final var image = manager.load(CubeImageData.class, key.image());
		final var type = key.type().type();
		return manager.map(image, new CubeTextureDataFunction(type));
	}
	
	@Override
	public Value<CubeTextureData> load(LoadContext manager, AssetKey ckey) {
		if(ckey instanceof CubeTextureAssetKey key) {
			return load(manager, key);
		}
		if(ckey instanceof ResultAssetKey key) {
			return load(manager, new CubeTextureAssetKey((String) key.result()));
		}
		if(ckey instanceof ResourceAssetKey key) {
			return load(manager, key.resourceName());
		}
		return null;
	}
	
	private static final class CubeTextureDataFunction
			implements Value1Function<CubeImageData, CubeTextureData> {
		
		private static final long serialVersionUID = 1L;
		private final Texture3DType type;
		
		public CubeTextureDataFunction(Texture3DType type) {
			this.type = Objects.requireNonNull(type);
		}
		
		@Override
		public CubeTextureData applyWithDest(CubeImageData value, CubeTextureData dest) {
			if(dest == null)
				return apply(value);
			if(value == null)
				return dest;
			return apply(value);
		}
		
		@Override
		public CubeTextureData apply(CubeImageData image) {
			return new CubeTextureData(image, type);
		}
		
	}
	
}
