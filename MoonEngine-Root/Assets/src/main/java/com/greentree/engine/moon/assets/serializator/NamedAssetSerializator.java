package com.greentree.engine.moon.assets.serializator;

import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.NamedAssetKey;
import com.greentree.engine.moon.assets.location.AssetLocation;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;


public class NamedAssetSerializator<T> implements AssetSerializator<T> {
	
	private final AssetLocation location;
	private final TypeInfo<T> TYPE;
	
	public NamedAssetSerializator(AssetLocation location, TypeInfo<T> TYPE) {
		this.location = location;
		this.TYPE = TYPE;
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey ckey) {
		if(ckey instanceof NamedAssetKey key) {
			final var real_key = location.getKey(key.name());
			return manager.canLoad(TYPE, real_key);
		}
		return false;
	}
	
	@Override
	public Value<T> load(LoadContext context, AssetKey ckey) {
		if(ckey instanceof NamedAssetKey key) {
			final var real_key = location.getKey(key.name());
			return context.load(TYPE, real_key);
		}
		return null;
	}
	
}
