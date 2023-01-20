package com.greentree.engine.moon.render.assets.buffer;

import com.greentree.common.renderer.buffer.AttributeGroupData;
import com.greentree.common.renderer.buffer.FloatBuffer;
import com.greentree.common.renderer.buffer.IntBuffer;
import com.greentree.common.renderer.buffer.VertexArrayData;
import com.greentree.common.renderer.mesh.StaticMesh;
import com.greentree.common.renderer.mesh.VertexComponent;
import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value1Function;

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
		
		private final VertexComponent[] components;
		
		public AttributeGroupDataFunction(VertexComponent[] components) {
			this.components = components;
		}
		
		@Override
		public VertexArrayData<IntBuffer, AttributeGroupData<FloatBuffer>> apply(StaticMesh mesh) {
			return mesh.getVerticesArray(components);
		}
		
	}
	
}
