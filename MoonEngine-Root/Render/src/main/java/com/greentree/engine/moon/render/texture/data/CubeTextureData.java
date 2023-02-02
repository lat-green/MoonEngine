package com.greentree.engine.moon.render.texture.data;

import java.io.Serializable;
import java.util.Objects;

public record CubeTextureData(CubeImageData image, Texture3DType type) implements Serializable {
	
	public CubeTextureData {
		Objects.requireNonNull(image);
		Objects.requireNonNull(type);
	}
	
}
