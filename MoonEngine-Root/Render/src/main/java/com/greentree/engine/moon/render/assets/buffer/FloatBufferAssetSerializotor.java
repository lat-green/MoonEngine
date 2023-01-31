package com.greentree.engine.moon.render.assets.buffer;

import com.greentree.commons.util.array.FloatArray;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value1Function;
import com.greentree.engine.moon.render.buffer.BufferUsing;
import com.greentree.engine.moon.render.buffer.FloatBuffer;

public class FloatBufferAssetSerializotor implements AssetSerializator<FloatBuffer> {
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(FloatArray.class, key);
	}
	
	@Override
	public Value<FloatBuffer> load(LoadContext manager, AssetKey ckey) {
		{
			final var array = manager.load(FloatArray.class, ckey);
			if(array != null)
				return manager.map(array, new FloatArray_TO_FloatBuffer_Function());
		}
		return null;
	}
	
	public class FloatArray_TO_FloatBuffer_Function
			implements Value1Function<FloatArray, FloatBuffer> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public FloatBuffer apply(FloatArray array) {
			return new FloatBuffer(array, BufferUsing.STATIC_DRAW);
		}
		
	}
	
}
