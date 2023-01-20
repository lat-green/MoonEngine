package com.greentree.engine.moon.render.assets.image.cube;

import java.util.Properties;

import com.greentree.common.renderer.texture.CubeImageData;
import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.AssetKeyType;
import com.greentree.commons.assets.key.ResourceAssetKey;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.serializator.manager.DefaultAssetManager;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value6Function;
import com.greentree.commons.image.image.ImageData;
import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.base.assets.text.PropertyAssetKey;

public class CubeImageAssetSerializator implements AssetSerializator<CubeImageData> {
	
	@Override
	public CubeImageData loadDefault(DefaultAssetManager manager, AssetKeyType type) {
		final ImageData DEFAULT = manager.loadDefault(ImageData.class);
		return new CubeImageData(DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT);
	}
	
	@Override
	public Value<CubeImageData> load(LoadContext context, AssetKey ckey) {
		if(ckey instanceof CubeImageAssetKey key) {
			final var posx = context.load(ImageData.class, key.posx());
			final var negx = context.load(ImageData.class, key.negx());
			final var posy = context.load(ImageData.class, key.posy());
			final var negy = context.load(ImageData.class, key.negy());
			final var posz = context.load(ImageData.class, key.posz());
			final var negz = context.load(ImageData.class, key.negz());
			return context.map(posx, negx, posy, negy, posz, negz, new CubeImageAsset());
		}
		{
			final var prop = context.load(Properties.class, ckey);
			if(prop != null) {
				final var posx_res = new ResourceAssetKey(new PropertyAssetKey(ckey, "posx"));
				final var negx_res = new ResourceAssetKey(new PropertyAssetKey(ckey, "negx"));
				final var posy_res = new ResourceAssetKey(new PropertyAssetKey(ckey, "posy"));
				final var negy_res = new ResourceAssetKey(new PropertyAssetKey(ckey, "negy"));
				final var posz_res = new ResourceAssetKey(new PropertyAssetKey(ckey, "posz"));
				final var negz_res = new ResourceAssetKey(new PropertyAssetKey(ckey, "negz"));
				
				
				final Value<ImageData> posx, negx, posy, negy, posz, negz;
				
				try(final var parallel = context.parallel()) {
					posx = parallel.load(ImageData.class, posx_res);
					negx = parallel.load(ImageData.class, negx_res);
					posy = parallel.load(ImageData.class, posy_res);
					negy = parallel.load(ImageData.class, negy_res);
					posz = parallel.load(ImageData.class, posz_res);
					negz = parallel.load(ImageData.class, negz_res);
				}
				
				return context.map(posx, negx, posy, negy, posz, negz, new CubeImageAsset());
			}
		}
		return null;
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return key instanceof CubeImageAssetKey || manager.canLoad(Properties.class, key);
	}
	
	private static final class CubeImageAsset implements
			Value6Function<ImageData, ImageData, ImageData, ImageData, ImageData, ImageData, CubeImageData> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public CubeImageData apply(ImageData posx, ImageData negx, ImageData posy, ImageData negy,
				ImageData posz, ImageData negz) {
			final var iter = IteratorUtil.iterable(posx, negx, posy, negy, posz, negz);
			for(var a : iter)
				if(a == null)
					return null;
				else
					for(var b : iter)
						if(a.getWidth() != b.getWidth() || a.getHeight() != b.getHeight())
							return null;
			return new CubeImageData(posx, negx, posy, negy, posz, negz);
		}
		
	}
	
}
