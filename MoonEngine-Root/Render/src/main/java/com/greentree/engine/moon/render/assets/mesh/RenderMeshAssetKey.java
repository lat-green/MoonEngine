package com.greentree.engine.moon.render.assets.mesh;

import java.util.Objects;

import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.ResultAssetKey;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.mesh.compoent.StaticMeshFaceComponent;


public record RenderMeshAssetKey(AssetKey mesh, StaticMeshFaceComponent... components)
		implements AssetKey {
	
	
	public RenderMeshAssetKey {
		Objects.requireNonNull(mesh);
		Objects.requireNonNull(components);
		for(var component : components)
			Objects.requireNonNull(component);
	}
	
	public RenderMeshAssetKey(StaticMesh mesh, StaticMeshFaceComponent... components) {
		this(new ResultAssetKey(mesh), components);
	}
	
}
