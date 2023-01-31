package com.greentree.engine.moon.render.assets.image.cube;

import java.util.Iterator;

import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.assets.key.AssetKey;


public record CubeImageAssetKey(AssetKey posx, AssetKey negx, AssetKey posy, AssetKey negy, AssetKey posz, AssetKey negz) implements AssetKey, Iterable<AssetKey> {
	
	@Override
	public Iterator<AssetKey> iterator() {
		return IteratorUtil.iterator(posx, negx, posy, negy, posz, negz);
	}
	
}
