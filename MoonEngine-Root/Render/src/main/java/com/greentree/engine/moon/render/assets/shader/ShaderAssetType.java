package com.greentree.engine.moon.render.assets.shader;

import com.greentree.engine.moon.assets.key.AssetKeyType;
import com.greentree.engine.moon.render.shader.ShaderType;


public record ShaderAssetType(ShaderType type) implements AssetKeyType {
}
