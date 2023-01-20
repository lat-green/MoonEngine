package com.greentree.engine.moon.base.assets.array;

import com.greentree.commons.assets.key.ResultAssetKey;
import com.greentree.commons.util.array.FloatArray;

public record FloatArrayAssetKey(FloatArray value) implements ResultAssetKey {
	private static final long serialVersionUID = 1L;

	public FloatArrayAssetKey(float...value) {
		this(new FloatArray(value));
	}
	
	public FloatArrayAssetKey(Float...value) {
		this(new FloatArray(floats(value)));
	}

	private static float[] floats(Float...value) {
		final var fs = new float[value.length];
		for(int i = 0; i < value.length; i++)
			fs[i] = value[i];
		return fs;
	}

	@Override
	public FloatArray result() {
		return value;
	}
	
}
