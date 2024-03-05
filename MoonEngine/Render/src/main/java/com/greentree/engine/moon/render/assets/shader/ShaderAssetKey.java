package com.greentree.engine.moon.render.assets.shader;

import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.ResourceAssetKey;
import com.greentree.engine.moon.render.shader.ShaderLanguage;
import com.greentree.engine.moon.render.shader.ShaderType;

import java.util.Objects;

public record ShaderAssetKey(AssetKey text, ShaderType shaderType, ShaderLanguage language)
        implements AssetKey {

    private static final long serialVersionUID = 1L;

    public ShaderAssetKey(String text, ShaderType type, ShaderLanguage language) {
        this(new ResourceAssetKey(text), type, language);
    }

    public ShaderAssetKey {
        Objects.requireNonNull(text);
        Objects.requireNonNull(shaderType);
        Objects.requireNonNull(language);
    }

}
