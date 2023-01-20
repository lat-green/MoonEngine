package com.greentree.engine.moon.base.assets.number;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value1Function;

public class DoubleAssetSerializator implements AssetSerializator<Double> {
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(String.class, key);
	}
	
	@Override
	public Value<Double> load(LoadContext manager, AssetKey ckey) {
		{
			final var str = manager.load(String.class, ckey);
			if(str != null)
				return manager.map(str, new StringToDouble());
		}
		return null;
	}
	
	private static final class StringToDouble implements Value1Function<String, Double> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public Double apply(String value) {
			return Double.parseDouble(value);
		}
		
	}
	
}
