package com.greentree.engine.moon.base.assets.number;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value1Function;

public class BooleanAssetSerializator implements AssetSerializator<Boolean> {
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(String.class, key);
	}
	
	@Override
	public Value<Boolean> load(LoadContext manager, AssetKey ckey) {
		{
			final var str = manager.load(String.class, ckey);
			if(str != null)
				return manager.map(str, new StringToBoolean());
		}
		return null;
	}
	
	private static final class StringToBoolean implements Value1Function<String, Boolean> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public Boolean apply(String str) {
			return switch(str) {
				case "1", "true", "True", "T" -> true;
				case "0", "false", "Frue", "F" -> false;
				default -> throw new IllegalArgumentException("Unexpected value: " + str);
			};
		}
		
	}
	
}
