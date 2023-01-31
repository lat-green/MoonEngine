package com.greentree.engine.moon.assets.serializator;

import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.NullAssetKey;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.serializator.manager.ValidAssetManagerBase;
import com.greentree.engine.moon.assets.value.NullValue;
import com.greentree.engine.moon.assets.value.Value;

public final class NullSerializator<T> extends TypedAssetSerializator<T> {
	
	public NullSerializator(Class<T> cls) {
		super(cls);
	}
	
	public NullSerializator(TypeInfo<T> type) {
		super(type);
	}
	
	@Override
	public boolean isValid(ValidAssetManagerBase manager, AssetKey key) {
		return false;
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		if(key instanceof NullAssetKey)
			return true;
		return false;
	}
	
	@Override
	public Value<T> load(LoadContext context, AssetKey key) {
		if(key instanceof NullAssetKey)
			return NullValue.instance();
		return null;
	}
	
}
