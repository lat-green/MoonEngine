package com.greentree.engine.moon.render.assets.buffer;

import java.util.Objects;

import com.greentree.common.renderer.buffer.AttributeGroupData;
import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.ResultAssetKey;

public final class AttributeGroupSizesAssetKey implements AssetKey {
	
	private static final long serialVersionUID = 1L;
	
	private final AssetKey group;
	
	private AttributeGroupSizesAssetKey(AssetKey group) {
		Objects.requireNonNull(group);
		this.group = group;
	}
	
	
	public AssetKey group() {
		return group;
	}
	
	public static AssetKey create(AssetKey group) {
		if(group instanceof AttributeGroupAssetKey key)
			return key.sizes();
		if(group instanceof ResultAssetKey key) {
			final var r = key.result();
			if(r instanceof AttributeGroupData<?> g) {
				return new ResultAssetKey(g.sizes());
			}
		}
		return new AttributeGroupSizesAssetKey(group);
	}
	
}
