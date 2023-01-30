package com.greentree.engine.moon.render.assets.mesh;

import java.io.IOException;

import com.greentree.common.renderer.mesh.GraphicsMesh;
import com.greentree.common.renderer.mesh.obj.ObjLoader;
import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value1Function;
import com.greentree.commons.data.resource.Resource;
import com.greentree.commons.util.exception.WrappedException;

public class MeshAssetSerializator implements AssetSerializator<GraphicsMesh> {
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(Resource.class, key);
	}
	
	@Override
	public Value<GraphicsMesh> load(LoadContext manager, AssetKey ckey) {
		if(manager.canLoad(Resource.class, ckey)) {
			final var resource = manager.load(Resource.class, ckey);
			return manager.map(resource, new ObjMeshValue1Function());
		}
		return null;
	}
	
	private static final class ObjMeshValue1Function
			implements Value1Function<Resource, GraphicsMesh> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public GraphicsMesh apply(Resource res) {
			if(res == null)
				return null;
			try(final var in = res.open()) {
				return ObjLoader.loadObjMesh(in);
			}catch(IOException e) {
				throw new WrappedException(e);
			}
		}
		
		
	}
	
}
