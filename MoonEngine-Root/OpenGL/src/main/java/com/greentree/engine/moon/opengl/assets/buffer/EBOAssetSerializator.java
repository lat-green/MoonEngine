package com.greentree.engine.moon.opengl.assets.buffer;

import org.lwjgl.system.MemoryStack;

import com.greentree.common.graphics.sgl.buffer.IntStaticDrawElementArrayBuffer;
import com.greentree.common.graphics.sgl.enums.gl.GLType;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value1Function;
import com.greentree.engine.moon.render.buffer.IntBuffer;

public final class EBOAssetSerializator
		implements AssetSerializator<IntStaticDrawElementArrayBuffer> {
	
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(IntBuffer.class, key);
	}
	
	@Override
	public Value<IntStaticDrawElementArrayBuffer> load(LoadContext manager, AssetKey ckey) {
		final var buf = manager.load(IntBuffer.class, ckey);
		if(buf != null)
			return manager.map(buf, new EBOAsset());
		return null;
	}
	
	private static final class EBOAsset
			implements Value1Function<IntBuffer, IntStaticDrawElementArrayBuffer> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public IntStaticDrawElementArrayBuffer applyWithDest(IntBuffer arr,
				IntStaticDrawElementArrayBuffer vbo) {
			final var INDEXES = arr.data().array;
			vbo.bind();
			try(final var stack = MemoryStack.create(INDEXES.length * GLType.FLOAT.size).push()) {
				vbo.setData(stack.ints(INDEXES));
			}
			vbo.unbind();
			return vbo;
		}
		
		@Override
		public IntStaticDrawElementArrayBuffer apply(IntBuffer arr) {
			final var INDEXES = arr.data().array;
			final var vbo = new IntStaticDrawElementArrayBuffer();
			vbo.bind();
			try(final var stack = MemoryStack.create(INDEXES.length * GLType.FLOAT.size).push()) {
				vbo.setData(stack.ints(INDEXES));
			}
			vbo.unbind();
			return vbo;
		}
		
	}
	
}
