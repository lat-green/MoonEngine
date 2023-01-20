package com.greentree.engine.moon.render.assets.buffer;

import java.util.Arrays;

import com.greentree.common.renderer.mesh.VertexComponent;
import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.ResultAssetKeyImpl;

public record MeshVAOAssetKey(AssetKey mesh, VertexComponent...components) implements AssetKey {	

	public MeshVAOAssetKey(Object obj, VertexComponent...components) {
		this(new ResultAssetKeyImpl(obj), components);
	}

	@Override
	public String toString() {
		return "MeshVAOAssetKey [mesh=" + mesh + ", components=" + Arrays.toString(components) + "]";
	}

}
