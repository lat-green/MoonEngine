package com.greentree.engine.moon.render.assets.shader;

import java.util.Objects;

import com.greentree.engine.moon.assets.key.AssetKeyType;
import com.greentree.engine.moon.render.shader.ShaderLanguage;
import com.greentree.engine.moon.render.shader.ShaderType;


public record ShaderAssetType(ShaderType type, ShaderLanguage language) implements AssetKeyType {
	
	public ShaderAssetType {
		Objects.requireNonNull(type);
		Objects.requireNonNull(language);
	}
	
}
