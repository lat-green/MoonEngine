package com.greentree.engine.moon.render.assets.texture;

import com.greentree.common.renderer.texture.Texture2DData;
import com.greentree.common.renderer.texture.Texture2DType;
import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.AssetKeyType;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.serializator.manager.DefaultAssetManager;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value1Function;
import com.greentree.commons.image.image.ImageData;

public class Texture2DAssetSerializator implements AssetSerializator<Texture2DData> {
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return key instanceof Texture2DAssetKey;
	}
	
	@Override
	public Value<Texture2DData> load(LoadContext manager, AssetKey ckey) {
		if(ckey instanceof Texture2DAssetKey key) {
			final var image = manager.load(ImageData.class, key.image());
			final var type = key.textureType();
			return manager.map(image, new Texture2DDataFunction(type));
		}
		return null;
	}
	
	@Override
	public Texture2DData loadDefault(DefaultAssetManager manager, AssetKeyType type) {
		final var img = manager.loadDefault(ImageData.class);
		return new Texture2DData(img, new Texture2DType());
	}
	
	private static final class Texture2DDataFunction
			implements Value1Function<ImageData, Texture2DData> {
		
		private static final long serialVersionUID = 1L;
		private final Texture2DType type;
		
		public Texture2DDataFunction(Texture2DType type) {
			this.type = type;
		}
		
		@Override
		public Texture2DData apply(ImageData value) {
			return new Texture2DData(value, type);
		}
		
	}
	
}
