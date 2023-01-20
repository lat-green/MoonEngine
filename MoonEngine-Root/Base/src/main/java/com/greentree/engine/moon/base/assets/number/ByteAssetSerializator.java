package com.greentree.engine.moon.base.assets.number;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value1Function;

public class ByteAssetSerializator implements AssetSerializator<Byte> {
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(String.class, key);
	}
	
	@Override
	public Value<Byte> load(LoadContext manager, AssetKey ckey) {
		{
			final var str = manager.load(String.class, ckey);
			if(str != null)
				return manager.map(str, new StringToByte());
		}
		return null;
	}
	
	private static final class StringToByte implements Value1Function<String, Byte> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public Byte apply(String str) {
			return Byte.parseByte(str);
		}
		
	}
	
}
