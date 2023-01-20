package com.greentree.engine.moon.render.assets.image;

import java.io.IOException;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.AssetKeyType;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.serializator.manager.DefaultAssetManager;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value1Function;
import com.greentree.commons.data.resource.Resource;
import com.greentree.commons.image.Color;
import com.greentree.commons.image.image.ImageData;
import com.greentree.commons.image.loader.ImageIOImageData;
import com.greentree.commons.image.loader.PNGImageData;
import com.greentree.commons.image.loader.TGAImageData;
import com.greentree.commons.util.exception.MultiException;


public class ImageAssetSerializator implements AssetSerializator<ImageData> {
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(Resource.class, key) || manager.canLoad(Color.class, key);
	}
	
	@Override
	public Value<ImageData> load(LoadContext manager, AssetKey key) {
		{
			final var res = manager.load(Resource.class, key);
			if(res != null)
				return manager.map(res, new ImageDataAsset());
		}
		{
			final var color = manager.load(Color.class, key);
			if(color != null)
				return manager.map(color, new ColorTextureAsset());
		}
		return null;
	}
	
	@Override
	public ImageData loadDefault(DefaultAssetManager manager, AssetKeyType type) {
		return new ImageData(manager.loadDefault(Color.class, Color.black));
	}
	
	private static final class ColorTextureAsset implements Value1Function<Color, ImageData> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public ImageData apply(Color color) {
			return new ImageData(color);
		}
		
	}
	
	private static final class ImageDataAsset implements Value1Function<Resource, ImageData> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public ImageData apply(Resource res) {
			if(res == null)
				return null;
			try(final var in = res.open()) {
				return ImageIOImageData.IMAGE_DATA_LOADER.loadImage(in);
			}catch(IOException e1) {
				try(final var in = res.open()) {
					return PNGImageData.IMAGE_DATA_LOADER.loadImage(in);
				}catch(IOException e2) {
					try(final var in = res.open()) {
						return TGAImageData.IMAGE_DATA_LOADER.loadImage(in);
					}catch(IOException e3) {
						throw new MultiException(e1, e2, e3);
					}
				}
			}
		}
		
	}
	
}
