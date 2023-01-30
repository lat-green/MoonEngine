package com.greentree.engine.moon.opengl.assets.buffer;

import org.lwjgl.system.MemoryStack;

import com.greentree.common.graphics.sgl.buffer.FloatStaticDrawArrayBuffer;
import com.greentree.common.graphics.sgl.enums.gl.GLType;
import com.greentree.common.renderer.buffer.FloatBuffer;
import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value1Function;

public final class VBOAssetSerializator implements AssetSerializator<FloatStaticDrawArrayBuffer> {
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(FloatBuffer.class, key);
	}
	
	@Override
	public Value<FloatStaticDrawArrayBuffer> load(LoadContext manager, AssetKey key) {
		final var buf = manager.load(FloatBuffer.class, key);
		if(buf != null)
			return manager.map(buf, new VBOAsset());
		return null;
	}
	
	private static final class VBOAsset
			implements Value1Function<FloatBuffer, FloatStaticDrawArrayBuffer> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public FloatStaticDrawArrayBuffer applyWithDest(FloatBuffer arr,
				FloatStaticDrawArrayBuffer vbo) {
			final var VERTEXS = arr.array().array;
			vbo.bind();
			try(final var stack = MemoryStack.create(VERTEXS.length * GLType.FLOAT.size).push()) {
				vbo.setData(stack.floats(VERTEXS));
			}
			vbo.unbind();
			return vbo;
		}
		
		@Override
		public FloatStaticDrawArrayBuffer apply(FloatBuffer arr) {
			final var VERTEXS = arr.array().array;
			final var vbo = new FloatStaticDrawArrayBuffer();
			vbo.bind();
			try(final var stack = MemoryStack.create(VERTEXS.length * GLType.FLOAT.size).push()) {
				vbo.setData(stack.floats(VERTEXS));
			}
			vbo.unbind();
			return vbo;
		}
		
	}
	
}
