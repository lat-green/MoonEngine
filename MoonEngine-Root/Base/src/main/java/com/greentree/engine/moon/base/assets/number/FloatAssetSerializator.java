package com.greentree.engine.moon.base.assets.number;

import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value1Function;

public class FloatAssetSerializator implements AssetSerializator<Float> {
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(String.class, key);
	}
	
	@Override
	public Value<Float> load(LoadContext manager, AssetKey ckey) {
		{
			final var str = manager.load(String.class, ckey);
			if(str != null)
				return manager.map(str, new StringToFloat());
		}
		return null;
	}
	
	private static final class StringToFloat implements Value1Function<String, Float> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public Float apply(String value) {
			return Float.parseFloat(value);
		}
		
	}
	
}
