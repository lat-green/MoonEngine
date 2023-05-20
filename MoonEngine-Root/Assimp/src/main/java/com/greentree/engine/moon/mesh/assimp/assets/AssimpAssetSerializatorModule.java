package com.greentree.engine.moon.mesh.assimp.assets;

import com.greentree.engine.moon.assets.serializator.manager.AssetManager;
import com.greentree.engine.moon.base.AssetManagerProperty;
import com.greentree.engine.moon.mesh.assimp.assets.mesh.AssimpMeshAssetSerializator;
import com.greentree.engine.moon.mesh.assimp.assets.mesh.AssimpSceneAssetSerializator;
import com.greentree.engine.moon.modules.EngineProperties;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.WriteProperty;

public class AssimpAssetSerializatorModule implements LaunchModule {
	
	@WriteProperty({AssetManagerProperty.class})
	@Override
	public void launch(EngineProperties context) {
		final var manager = context.getData(AssetManager.class);
		
		manager.addSerializator(new AssimpSceneAssetSerializator());
		manager.addSerializator(new AssimpMeshAssetSerializator());
	}
	
}
