package com.greentree.engine.moon.base.assets.text;

import java.util.Properties;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.serializator.manager.DeepValidAssetManagerBase;
import com.greentree.commons.assets.serializator.manager.ValidAssetManagerBase;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value2Function;

public class PropertyStringAssetSerializor implements AssetSerializator<String> {
	
	@Override
	public Value<String> load(LoadContext manager, AssetKey ckey) {
		if(ckey instanceof PropertyAssetKey key) {
			final var prop = manager.load(Properties.class, key.properties());
			final var name = manager.load(String.class, key.name());
			return manager.map(prop, name, new PropertyAsset());
		}
		return null;
	}
	
	@Override
	public boolean isDeepValid(DeepValidAssetManagerBase manager, AssetKey key) {
		if(key instanceof PropertyAssetKey k)
			if(manager.isValid(Properties.class, k.properties())
					&& manager.isValid(String.class, k.name())) {
				final var prop = manager.loadData(Properties.class, k.properties());
				final var name = manager.loadData(String.class, k.name());
				return prop.containsKey(name);
			}
		return false;
	}
	
	@Override
	public boolean isValid(ValidAssetManagerBase manager, AssetKey key) {
		if(key instanceof PropertyAssetKey k)
			return manager.isValid(Properties.class, k.properties())
					&& manager.isValid(String.class, k.name());
		return false;
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return key instanceof PropertyAssetKey;
	}
	
	private static final class PropertyAsset implements Value2Function<Properties, String, String> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public String apply(Properties prop, String name) {
			return prop.getProperty(name);
		}
		
	}
	
}
