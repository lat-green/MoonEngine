package com.greentree.engine.moon.render.other;

import java.io.Serializable;
import java.util.Iterator;

import com.greentree.commons.image.image.ImageData;
import com.greentree.commons.util.iterator.IteratorUtil;

public record CubeImageData(ImageData posx, ImageData negx, ImageData posy, ImageData negy, ImageData posz, ImageData negz) implements Iterable<ImageData>, Serializable {

	@Override
	public Iterator<ImageData> iterator() {
		return IteratorUtil.iterator(posx, negx, posy, negy, posz, negz);
	}
	
}
