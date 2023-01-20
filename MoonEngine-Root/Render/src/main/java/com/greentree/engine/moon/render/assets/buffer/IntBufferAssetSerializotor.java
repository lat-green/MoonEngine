package com.greentree.engine.moon.render.assets.buffer;

import com.greentree.common.renderer.buffer.IntBuffer;
import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value1Function;
import com.greentree.commons.util.array.IntArray;

public class IntBufferAssetSerializotor implements AssetSerializator<IntBuffer> {
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(IntArray.class, key);
	}
	
	@Override
	public Value<IntBuffer> load(LoadContext manager, AssetKey key) {
		{
			final var array = manager.load(IntArray.class, key);
			if(array != null)
				return manager.map(array, new IntBufferAsset());
		}
		return null;
	}
	
	public class IntBufferAsset implements Value1Function<IntArray, IntBuffer> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public IntBuffer apply(IntArray array) {
			return new IntBuffer(array);
		}
		
	}
	
}
