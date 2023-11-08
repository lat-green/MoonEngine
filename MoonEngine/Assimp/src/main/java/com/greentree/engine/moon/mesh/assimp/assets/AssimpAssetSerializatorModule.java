package com.greentree.engine.moon.mesh.assimp.assets;

import com.greentree.engine.moon.base.AssetManagerProperty;
import com.greentree.engine.moon.base.property.modules.WriteProperty;
import com.greentree.engine.moon.mesh.assimp.assets.mesh.AssimpMeshAssetSerializator;
import com.greentree.engine.moon.mesh.assimp.assets.mesh.AssimpSceneAssetSerializator;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.property.EngineProperties;

public class AssimpAssetSerializatorModule implements LaunchModule {

    @WriteProperty({AssetManagerProperty.class})
    @Override
    public void launch(EngineProperties context) {
        final var manager = context.get(AssetManagerProperty.class).manager;
        manager.addSerializator(new AssimpSceneAssetSerializator());
        manager.addSerializator(new AssimpMeshAssetSerializator());
    }

}
