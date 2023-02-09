package com.greentree.engine.moon.opengl.assets.mesh;

import org.lwjgl.system.MemoryStack;

import com.greentree.common.graphics.sgl.buffer.FloatStaticDrawArrayBuffer;
import com.greentree.common.graphics.sgl.enums.gl.GLType;
import com.greentree.common.graphics.sgl.vao.GLVertexArray;
import com.greentree.common.graphics.sgl.vao.GLVertexArray.AttributeGroup;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value1Function;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.mesh.compoent.StaticMeshFaceComponent;
import com.greentree.engine.moon.opengl.adapter.VAORenderMeshAddapter;
import com.greentree.engine.moon.render.pipeline.RenderLibrary;

public final class MeshAddapterAssetSerializator implements AssetSerializator<VAORenderMeshAddapter> {
	
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(StaticMesh.class, key);
	}
	
	@Override
	public Value<VAORenderMeshAddapter> load(LoadContext context, AssetKey key) {
		if(context.canLoad(StaticMesh.class, key)) {
			final var mesh = context.load(StaticMesh.class, key);
			return context.map(mesh, MeshAddapterAssetFunction.INSTANCE);
		}
		return null;
	}
	
	private static final class MeshAddapterAssetFunction implements Value1Function<StaticMesh, VAORenderMeshAddapter> {
		
		private static final long serialVersionUID = 1L;
		public static final MeshAddapterAssetFunction INSTANCE = new MeshAddapterAssetFunction();
		
		@Override
		public VAORenderMeshAddapter apply(StaticMesh value) {
			final var vao = getVAO(value, RenderLibrary.COMPONENTS);
			return new VAORenderMeshAddapter(vao);
		}
		
		@Override
		public VAORenderMeshAddapter applyWithDest(StaticMesh value, VAORenderMeshAddapter dest) {
			try {
				final var mesh = apply(value);
				dest.close();
				return mesh;
			}catch(Exception e) {
				e.printStackTrace();
				return dest;
			}
		}
		
	}
	
	public static GLVertexArray getVAO(StaticMesh mesh, StaticMeshFaceComponent... components) {
		final var g = mesh.getAttributeGroup(components);
		final var vbo = getVBO(g.vertex());
		final var vao = new GLVertexArray(AttributeGroup.of(vbo, g.sizes()));
		vbo.close();
		return vao;
	}
	
	public static FloatStaticDrawArrayBuffer getVBO(float[] VERTEXS) {
		final var vbo = new FloatStaticDrawArrayBuffer();
		vbo.bind();
		try(final var stack = MemoryStack.create(VERTEXS.length * GLType.FLOAT.size).push()) {
			vbo.setData(stack.floats(VERTEXS));
		}
		vbo.unbind();
		return vbo;
	}
	
}
