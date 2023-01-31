package com.greentree.engine.moon.render.assets.buffer;

import java.util.Objects;

import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.ResultAssetKey;
import com.greentree.engine.moon.render.buffer.AttributeGroupData;

public final class AttributeGroupVertexAssetKey implements AssetKey {
	
	private static final long serialVersionUID = 1L;
	
	private final AssetKey group;
	
	private AttributeGroupVertexAssetKey(AssetKey group) {
		Objects.requireNonNull(group);
		this.group = group;
	}
	
	
	public AssetKey group() {
		return group;
	}
	
	public static AssetKey create(AssetKey group) {
		if(group instanceof AttributeGroupAssetKey key)
			return key.vbo();
		if(group instanceof ResultAssetKey key) {
			final var r = key.result();
			if(r instanceof AttributeGroupData<?> g) {
				return new ResultAssetKey(g.vertex());
			}
		}
		return new AttributeGroupVertexAssetKey(group);
	}
	
}
