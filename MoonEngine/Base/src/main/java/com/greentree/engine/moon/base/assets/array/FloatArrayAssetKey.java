package com.greentree.engine.moon.base.assets.array;

import com.greentree.commons.util.array.FloatArray;
import com.greentree.engine.moon.assets.key.AssetKey;

public record FloatArrayAssetKey(FloatArray value) implements AssetKey {
	
	private static final long serialVersionUID = 1L;
	
	public FloatArrayAssetKey(float... value) {
		this(new FloatArray(value));
	}
	
	public FloatArrayAssetKey(Float... value) {
		this(new FloatArray(floats(value)));
	}
	
	private static float[] floats(Float... value) {
		final var fs = new float[value.length];
		for(int i = 0; i < value.length; i++)
			fs[i] = value[i];
		return fs;
	}
	
}
