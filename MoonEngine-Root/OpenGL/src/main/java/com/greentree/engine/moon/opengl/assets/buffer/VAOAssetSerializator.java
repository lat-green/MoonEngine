package com.greentree.engine.moon.opengl.assets.buffer;

import com.greentree.common.graphics.sgl.buffer.IntStaticDrawElementArrayBuffer;
import com.greentree.common.graphics.sgl.vao.GLVertexArray;
import com.greentree.common.graphics.sgl.vao.GLVertexArray.AttributeGroup;
import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value1Function;
import com.greentree.engine.moon.render.buffer.VertexArrayData;


public class VAOAssetSerializator implements AssetSerializator<GLVertexArray> {
	
	private static final TypeInfo<VertexArrayData<IntStaticDrawElementArrayBuffer, AttributeGroup>> VERTEX_ARRAY_DATA_TYPE = TypeInfoBuilder
			.getTypeInfo(VertexArrayData.class, IntStaticDrawElementArrayBuffer.class,
					AttributeGroup.class);
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(VERTEX_ARRAY_DATA_TYPE, key);
	}
	
	@Override
	public Value<GLVertexArray> load(LoadContext context, AssetKey key) {
		final var data = context.load(VERTEX_ARRAY_DATA_TYPE, key);
		if(data != null)
			return context.map(data, new VAOFunction());
		return null;
	}
	
	private static final class VAOFunction implements
			Value1Function<VertexArrayData<IntStaticDrawElementArrayBuffer, AttributeGroup>, GLVertexArray> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public GLVertexArray apply(
				VertexArrayData<IntStaticDrawElementArrayBuffer, AttributeGroup> data) {
			final var ebo = data.ebo();
			final var vbos = data.vbos();
			if(ebo == null)
				return new GLVertexArray(vbos);
			return new GLVertexArray(ebo, vbos);
		}
		
	}
	
}
