package com.greentree.engine.moon.render.assets.buffer;

import java.util.Arrays;

import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.ResultAssetKey;
import com.greentree.engine.moon.mesh.compoent.StaticMeshFaceComponent;

public record MeshVAOAssetKey(AssetKey mesh, StaticMeshFaceComponent... components)
		implements AssetKey {
	
	@SafeVarargs
	public MeshVAOAssetKey(Object obj, StaticMeshFaceComponent... components) {
		this(new ResultAssetKey(obj), components);
	}
	
	@Override
	public String toString() {
		return "MeshVAOAssetKey [mesh=" + mesh + ", components=" + Arrays.toString(components)
				+ "]";
	}
	
}
