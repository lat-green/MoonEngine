package com.greentree.engine.moon.render.assets.material;

import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value2Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;
import com.greentree.engine.moon.render.material.Material;
import com.greentree.engine.moon.render.material.MaterialProperties;
import com.greentree.engine.moon.render.shader.ShaderProgramData;

public final class PBRMaterialAssetSerializator implements AssetSerializator<Material> {

    @Override
    public Asset<Material> load(AssetManager manager, AssetKey key) {
        var program = manager.load(ShaderProgramData.class, "shader/pbr_mapping/pbr_mapping.glsl");
        var properties = manager.load(MaterialProperties.class, key);
        return AssetKt.map(program, properties, new PBRMaterialAssetSerializatorFunction());
    }

    private static final class PBRMaterialAssetSerializatorFunction
            implements Value2Function<ShaderProgramData, MaterialProperties, Material> {

        private static final long serialVersionUID = 1L;

        @Override
        public Material apply(ShaderProgramData program, MaterialProperties properties) {
            return new Material(program, properties);
        }

    }

}
