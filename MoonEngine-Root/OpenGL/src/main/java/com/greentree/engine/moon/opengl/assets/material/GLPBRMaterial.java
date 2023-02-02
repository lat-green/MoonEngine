package com.greentree.engine.moon.opengl.assets.material;

import java.io.Serializable;

import com.greentree.common.graphics.sgl.texture.gl.GLTexture2DImpl;

@Deprecated
public record GLPBRMaterial(GLTexture2DImpl albedo, GLTexture2DImpl normal,
		GLTexture2DImpl metallic, GLTexture2DImpl roughness, GLTexture2DImpl displacement,
		GLTexture2DImpl ambientOcclusion) implements Serializable {
}
