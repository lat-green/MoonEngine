package com.greentree.engine.moon.render.assets.mesh;

import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value1Function;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.mesh.compoent.StaticMeshFaceComponent;
import com.greentree.engine.moon.render.mesh.RenderMesh;
import com.greentree.engine.moon.render.pipeline.RenderLibrary;

public final class RenderMeshAssetSerializator implements AssetSerializator<RenderMesh> {
	
	
	private final RenderLibrary library;
	
	
	
	public RenderMeshAssetSerializator(RenderLibrary library) {
		this.library = library;
	}
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return key instanceof RenderMeshAssetKey;
	}
	
	@Override
	public Value<RenderMesh> load(LoadContext context, AssetKey ckey) {
		if(ckey instanceof RenderMeshAssetKey key) {
			final var components = key.components();
			final var mesh = context.load(StaticMesh.class, key.mesh());
			return context.map(mesh, new StaticMesh_RenderMesh_Function(library, components));
		}
		return null;
	}
	
	private static final class StaticMesh_RenderMesh_Function
			implements Value1Function<StaticMesh, RenderMesh> {
		
		private static final long serialVersionUID = 1L;
		private final StaticMeshFaceComponent[] components;
		private final RenderLibrary library;
		
		public StaticMesh_RenderMesh_Function(RenderLibrary library,
				StaticMeshFaceComponent[] components) {
			this.components = components;
			this.library = library;
		}
		
		@Override
		public RenderMesh apply(StaticMesh value) {
			return library.build(value, components);
		}
		
	}
	
}
