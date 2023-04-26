package com.greentree.engine.moon.render.assets.texture;

import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.render.material.Property;
import com.greentree.engine.moon.render.pipeline.RenderLibrary;
import com.greentree.engine.moon.render.texture.CubeTextureData;
import com.greentree.engine.moon.render.texture.Texture2DData;

public class TextureAssetSerializator implements AssetSerializator<Property> {
	
	private final RenderLibrary library;
	
	public TextureAssetSerializator(RenderLibrary library) {
		this.library = library;
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(Texture2DData.class, key) || manager.canLoad(CubeTextureData.class, key);
	}
	
	@Override
	public Value<Property> load(LoadContext manager, AssetKey key) {
		if(manager.canLoad(Texture2DData.class, key)) {
			var data = manager.load(Texture2DData.class, key);
			return manager.map(data, x -> library.build(x));
		}
		if(manager.canLoad(CubeTextureData.class, key)) {
			var data = manager.load(CubeTextureData.class, key);
			return manager.map(data, x -> library.build(x));
		}
		return null;
	}
	
}
