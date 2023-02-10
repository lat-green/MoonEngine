package com.greentree.engine.moon.render.texture.data;

import java.io.Serializable;
import java.util.Objects;

public record CubeTextureData(CubeImageData image, Texture3DType type) implements Serializable {
	
	public CubeTextureData {
		Objects.requireNonNull(image, "image is null");
		Objects.requireNonNull(type, "type is null");
	}
	
}
