
package com.greentree.engine.moon.render.assets.buffer;

import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value1Function;
import com.greentree.engine.moon.render.buffer.AttributeGroupData;
import com.greentree.engine.moon.render.buffer.FloatBuffer;
import com.greentree.engine.moon.render.buffer.IntBuffer;
import com.greentree.engine.moon.render.buffer.VertexArrayData;

public final class VertexArrayEBOAssetSerializator implements AssetSerializator<IntBuffer> {
	
	private static final TypeInfo<VertexArrayData<IntBuffer, AttributeGroupData<FloatBuffer>>> TYPE = TypeInfoBuilder
			.getTypeInfo(VertexArrayData.class, IntBuffer.class,
					TypeInfoBuilder.getTypeInfo(AttributeGroupData.class, FloatBuffer.class));
	
	private static final class VBOsAsset implements
			Value1Function<VertexArrayData<IntBuffer, AttributeGroupData<FloatBuffer>>, IntBuffer> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public IntBuffer apply(VertexArrayData<IntBuffer, AttributeGroupData<FloatBuffer>> t) {
			return t.ebo();
		}
		
	}
	
	@Override
	public Value<IntBuffer> load(LoadContext manager, AssetKey ckey) {
		if(ckey instanceof VertexArrayEBOAssetKey key) {
			final var array = manager.load(TYPE, key.array());
			return manager.map(array, new VBOsAsset());
		}
		return null;
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		if(key instanceof VertexArrayEBOAssetKey k) {
			return true;
		}
		System.out.println(key);
		return false;
	}
	
}
