package com.greentree.engine.moon.render.assets;

import com.greentree.engine.moon.base.AssetManagerProperty;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.base.property.modules.WriteProperty;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.property.EngineProperties;
import com.greentree.engine.moon.render.assets.image.ImageAssetSerializator;
import com.greentree.engine.moon.render.assets.image.cube.CubeImageAssetSerializator;
import com.greentree.engine.moon.render.assets.material.PBRMaterialAssetSerializator;
import com.greentree.engine.moon.render.assets.material.PBRMaterialPropertiesAssetSerializator;
import com.greentree.engine.moon.render.assets.shader.ShaderDataAssetSerializator;
import com.greentree.engine.moon.render.assets.shader.ShaderProgramDataAssetSerializator;
import com.greentree.engine.moon.render.assets.texture.Texture2DAssetSerializator;
import com.greentree.engine.moon.render.assets.texture.TextureAssetSerializator;
import com.greentree.engine.moon.render.assets.texture.cube.CubeTextureAssetSerializator;
import com.greentree.engine.moon.render.pipeline.RenderLibraryProperty;

public class RenderAssetSerializatorModule implements LaunchModule {

    @ReadProperty({RenderLibraryProperty.class})
    @WriteProperty({AssetManagerProperty.class})
    @Override
    public void launch(EngineProperties context) {
        final var library = context.get(RenderLibraryProperty.class).library();
        final var manager = context.get(AssetManagerProperty.class).manager();
        manager.addSerializator(new CubeImageAssetSerializator());
        manager.addSerializator(new CubeTextureAssetSerializator());
        manager.addSerializator(new ImageAssetSerializator());
        manager.addSerializator(new Texture2DAssetSerializator());
        manager.addSerializator(new ShaderDataAssetSerializator());
        manager.addSerializator(new ShaderProgramDataAssetSerializator());
        manager.addSerializator(new PBRMaterialPropertiesAssetSerializator());
        manager.addSerializator(new PBRMaterialAssetSerializator());
        manager.addSerializator(new TextureAssetSerializator(library));
    }

}
