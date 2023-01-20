package com.greentree.engine.moon.render.assets.buffer;

import com.greentree.common.renderer.buffer.FloatBuffer;
import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value1Function;
import com.greentree.commons.util.array.FloatArray;

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
				return manager.map(array, new FloatBufferAssetFunction());
		}
		return null;
	}
	
	public class FloatBufferAssetFunction implements Value1Function<FloatArray, FloatBuffer> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public FloatBuffer apply(FloatArray array) {
			return new FloatBuffer(array);
		}
		
	}
	
}
