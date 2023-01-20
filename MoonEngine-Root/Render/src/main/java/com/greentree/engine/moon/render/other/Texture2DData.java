package com.greentree.engine.moon.render.other;

import java.io.Serializable;
import java.util.Objects;

import com.greentree.commons.image.PixelFormat;
import com.greentree.commons.image.image.ImageData;

public record Texture2DData(ImageData image, Texture2DType type) implements Serializable {
	
	
	public Texture2DData {
		Objects.requireNonNull(image);
		Objects.requireNonNull(type);
	}
	
	public PixelFormat getFormat() {
		return image.getFormat();
	}
	
	public int getHeight() {
		return image.getHeight();
	}
	
	public int getWidth() {
		return image.getHeight();
	}
	
}
