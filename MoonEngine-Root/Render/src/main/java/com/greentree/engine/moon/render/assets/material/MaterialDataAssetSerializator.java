package com.greentree.engine.moon.render.assets.material;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.value.ConstValue;
import com.greentree.commons.assets.value.Value;

public class MaterialDataAssetSerializator implements AssetSerializator<MaterialData> {
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return key instanceof MaterialAssetKey;
	}
	
	@Override
	public Value<MaterialData> load(LoadContext manager, AssetKey ckey) {
		if(ckey instanceof MaterialAssetKey key)
			return ConstValue.newValue(new MaterialData(key.properties(), key.shader()));
		return null;
	}
	
}
