package com.greentree.engine.moon.render.assets.buffer;

import com.greentree.common.renderer.buffer.AttributeGroupData;
import com.greentree.common.renderer.buffer.FloatBuffer;
import com.greentree.common.renderer.buffer.IntBuffer;
import com.greentree.common.renderer.buffer.VertexArrayData;
import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value1Function;
import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;


public final class VertexArrayVBOsAssetSerializator
		implements AssetSerializator<Iterable<AttributeGroupData<FloatBuffer>>> {
	
	private static final TypeInfo<VertexArrayData<IntBuffer, AttributeGroupData<FloatBuffer>>> TYPE = TypeInfoBuilder
			.getTypeInfo(VertexArrayData.class, IntBuffer.class,
					TypeInfoBuilder.getTypeInfo(AttributeGroupData.class, FloatBuffer.class));
	
	private static final class VBOsAsset implements
			Value1Function<VertexArrayData<IntBuffer, AttributeGroupData<FloatBuffer>>, Iterable<AttributeGroupData<FloatBuffer>>> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public Iterable<AttributeGroupData<FloatBuffer>> apply(
				VertexArrayData<IntBuffer, AttributeGroupData<FloatBuffer>> t) {
			return t.vbos();
		}
		
	}
	
	@Override
	public Value<Iterable<AttributeGroupData<FloatBuffer>>> load(LoadContext manager,
			AssetKey ckey) {
		if(ckey instanceof VertexArrayVBOsAssetKey key) {
			final var array = manager.load(TYPE, key.array());
			return manager.map(array, new VBOsAsset());
		}
		return null;
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		if(key instanceof VertexArrayVBOsAssetKey k) {
			return true;
		}
		return false;
	}
	
}
