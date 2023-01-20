package com.greentree.engine.moon.render.other;

import java.io.Serializable;
import java.util.Objects;

public record CubeTextureData(CubeImageData image, Texture3DType type) implements Serializable {
	
	public CubeTextureData {
		Objects.requireNonNull(image);
		Objects.requireNonNull(type);
	}
	
}
