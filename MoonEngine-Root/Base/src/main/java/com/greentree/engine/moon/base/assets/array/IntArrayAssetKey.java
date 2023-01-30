package com.greentree.engine.moon.base.assets.array;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.util.array.IntArray;

public record IntArrayAssetKey(IntArray value) implements AssetKey {
	
	private static final long serialVersionUID = 1L;
	
	public IntArrayAssetKey(int... value) {
		this(new IntArray(value));
	}
	
	public IntArrayAssetKey(Integer... value) {
		this(new IntArray(ints(value)));
	}
	
	private static int[] ints(Integer... value) {
		final var fs = new int[value.length];
		for(int i = 0; i < value.length; i++)
			fs[i] = value[i];
		return fs;
	}
	
}
