package com.greentree.engine.moon.base.assets.text;

import java.util.Properties;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.serializator.manager.DeepValidAssetManagerBase;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value2Function;

public class PropertyStringAssetSerializor implements AssetSerializator<String> {
	
	@Override
	public Value<String> load(LoadContext manager, AssetKey ckey) {
		if(ckey instanceof PropertyAssetKey key) {
			final var prop = manager.load(Properties.class, key.properties());
			final var name = manager.load(String.class, key.name());
			return manager.map(prop, name, new PropertyAssetFunction());
		}
		return null;
	}
	
	@Override
	public boolean isDeepValid(DeepValidAssetManagerBase manager, AssetKey key) {
		if(key instanceof PropertyAssetKey k)
			if(manager.isDeepValid(Properties.class, k.properties())
					&& manager.isDeepValid(String.class, k.name())) {
				final var prop = manager.loadData(Properties.class, k.properties());
				final var name = manager.loadData(String.class, k.name());
				return prop.containsKey(name);
			}
		return false;
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		if(key instanceof PropertyAssetKey k)
			return manager.canLoad(Properties.class, k.properties())
					&& manager.canLoad(String.class, k.name());
		return false;
	}
	
	private static final class PropertyAssetFunction
			implements Value2Function<Properties, String, String> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public String apply(Properties prop, String name) {
			return prop.getProperty(name);
		}
		
	}
	
}
