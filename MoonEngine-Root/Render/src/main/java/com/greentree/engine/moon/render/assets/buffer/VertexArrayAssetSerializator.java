package com.greentree.engine.moon.render.assets.buffer;

import java.util.ArrayList;

import com.greentree.commons.util.array.FloatArray;
import com.greentree.commons.util.array.IntArray;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value1Function;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.mesh.compoent.StaticMeshFaceComponent;
import com.greentree.engine.moon.mesh.compoent.VertexArrayBuilder;
import com.greentree.engine.moon.render.buffer.AttributeGroupData;
import com.greentree.engine.moon.render.buffer.BufferUsing;
import com.greentree.engine.moon.render.buffer.FloatBuffer;
import com.greentree.engine.moon.render.buffer.IntBuffer;
import com.greentree.engine.moon.render.buffer.VertexArrayData;

public class VertexArrayAssetSerializator
		implements AssetSerializator<VertexArrayData<IntBuffer, AttributeGroupData<FloatBuffer>>> {
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		if(key instanceof MeshVAOAssetKey k)
			return manager.canLoad(StaticMesh.class, k.mesh());
		return false;
	}
	
	@Override
	public Value<VertexArrayData<IntBuffer, AttributeGroupData<FloatBuffer>>> load(
			LoadContext manager, AssetKey ckey) {
		if(ckey instanceof MeshVAOAssetKey key) {
			final var mesh = manager.load(StaticMesh.class, key.mesh());
			return manager.map(mesh, new AttributeGroupDataFunction(key.components()));
		}
		return null;
	}
	
	private static final class AttributeGroupDataFunction implements
			Value1Function<StaticMesh, VertexArrayData<IntBuffer, AttributeGroupData<FloatBuffer>>> {
		
		private static final long serialVersionUID = 1L;
		
		private final StaticMeshFaceComponent[] components;
		
		public AttributeGroupDataFunction(StaticMeshFaceComponent[] components) {
			this.components = components;
		}
		
		@Override
		public VertexArrayData<IntBuffer, AttributeGroupData<FloatBuffer>> apply(StaticMesh mesh) {
			final var builder = new VertexArrayBuilder();
			final var vao = builder.getVerticesArray(mesh, components);
			final var attrs = new ArrayList<AttributeGroupData<FloatBuffer>>();
			for(var a : vao.vbos())
				attrs.add(new AttributeGroupData<FloatBuffer>(
						new FloatBuffer(new FloatArray(a.vertex()), BufferUsing.STATIC_DRAW),
						new IntArray(a.sizes())));
			
			if(vao.indecies() == null) {
				return new VertexArrayData<>(null, attrs);
			}
			return new VertexArrayData<IntBuffer, AttributeGroupData<FloatBuffer>>(
					new IntBuffer(new IntArray(vao.indecies()), BufferUsing.STATIC_DRAW), attrs);
		}
		
	}
	
}
