package com.greentree.engine.moon.render.assets.color;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.AssetKeyType;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.serializator.manager.DeepValidAssetManagerBase;
import com.greentree.commons.assets.serializator.manager.DefaultAssetManager;
import com.greentree.commons.assets.serializator.manager.ValidAssetManagerBase;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.image.Color;


public class ColorAssetSerializer implements AssetSerializator<Color> {
	
	@Override
	public boolean isDeepValid(DeepValidAssetManagerBase manager, AssetKey key) {
		return false;
	}
	
	@Override
	public boolean isValid(ValidAssetManagerBase manager, AssetKey key) {
		return false;
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return false;
	}
	
	@Override
	public Color loadDefault(DefaultAssetManager manager, AssetKeyType type) {
		return Color.white;
	}
	
	@Override
	public Value<Color> load(LoadContext context, AssetKey key) {
		return null;
	}
	
}
