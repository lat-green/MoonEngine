package com.greentree.engine.moon.render.assets.buffer;


import com.greentree.commons.util.array.IntArray;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value1Function;
import com.greentree.engine.moon.render.buffer.BufferUsing;
import com.greentree.engine.moon.render.buffer.IntBuffer;

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
			return new IntBuffer(array, BufferUsing.STATIC_DRAW);
		}
		
	}
	
}
