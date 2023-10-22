package com.greentree.engine.moon.render.texture;

import java.io.Serializable;
import java.util.Iterator;

import com.greentree.commons.image.image.ImageData;
import com.greentree.commons.util.iterator.IteratorUtil;

public record CubeImageData(ImageData posx, ImageData negx, ImageData posy, ImageData negy, ImageData posz, ImageData negz) implements Iterable<ImageData>, Serializable {

	public CubeImageData {
		var iter = IteratorUtil.iterable(posx, negx, posy, negy, posz, negz);
		for(var a : iter)
			for(var b : iter)
				if(a.getWidth() != b.getWidth() || a.getHeight() != b.getHeight())
					throw new IllegalArgumentException("different size "+ a +" and " + b);
	}

	@Override
	public Iterator<ImageData> iterator() {
		return IteratorUtil.iterator(posx, negx, posy, negy, posz, negz);
	}
	
}
