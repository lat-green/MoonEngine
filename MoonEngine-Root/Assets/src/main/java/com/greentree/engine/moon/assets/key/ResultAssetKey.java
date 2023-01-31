package com.greentree.engine.moon.assets.key;

import java.util.Objects;

public record ResultAssetKey(Object result) implements AssetKey {
	
	public ResultAssetKey {
		Objects.requireNonNull(result);
	}
	
	@Override
	public String toString() {
		return result.toString();
	}
	
	
	
}
