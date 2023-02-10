package com.greentree.engine.moon.render.assets.texture.cube;

import java.util.Objects;

import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value1Function;
import com.greentree.engine.moon.render.texture.data.CubeImageData;
import com.greentree.engine.moon.render.texture.data.CubeTextureData;
import com.greentree.engine.moon.render.texture.data.Texture3DType;

public class CubeTextureAssetSerializator implements AssetSerializator<CubeTextureData> {
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return key instanceof CubeTextureAssetKey;
	}
	
	@Override
	public Value<CubeTextureData> load(LoadContext manager, AssetKey ckey) {
		if(ckey instanceof CubeTextureAssetKey key) {
			final var image = manager.load(CubeImageData.class, key.image());
			final var type = key.type().type();
			return manager.map(image, new CubeTextureDataFunction(type));
		}
		return null;
	}
	
	private static final class CubeTextureDataFunction implements Value1Function<CubeImageData, CubeTextureData> {
		
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
