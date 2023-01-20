package com.greentree.engine.moon.render.other;

import java.io.Serializable;

public record Texture3DType(Filtering filteringMin, Filtering filteringMag, Wrapping wrapX, Wrapping wrapY, Wrapping wrapZ) implements Serializable {
	
	public static final Filtering DEFAULT_MIN_FILTERING = Filtering.NEAREST;
	public static final Filtering DEFAULT_MAG_FILTERING = Filtering.NEAREST;
	
	public static final Wrapping DEFAULT_X_WRAPPING = Wrapping.CLAMP_TO_EDGE;
	public static final Wrapping DEFAULT_Y_WRAPPING = Wrapping.CLAMP_TO_EDGE;
	public static final Wrapping DEFAULT_Z_WRAPPING = Wrapping.CLAMP_TO_EDGE;
	
	public Texture3DType(Filtering filteringMin, Filtering filteringMag, Wrapping wrap) {
		this(filteringMin, filteringMag, wrap, wrap, wrap);
	}
	
	public Texture3DType(Wrapping wrap) {
		this(DEFAULT_MIN_FILTERING, DEFAULT_MAG_FILTERING, wrap, wrap, wrap);
	}
	
	public Texture3DType() {
		this(DEFAULT_MIN_FILTERING, DEFAULT_MAG_FILTERING, DEFAULT_X_WRAPPING, DEFAULT_Y_WRAPPING, DEFAULT_Z_WRAPPING);
	}
	
	public Texture3DType(Filtering filteringMin, Filtering filteringMag) {
		this(filteringMin, filteringMag, DEFAULT_X_WRAPPING, DEFAULT_Y_WRAPPING, DEFAULT_Z_WRAPPING);
	}
	
	public Texture3DType(Filtering filtering, Wrapping wrap) {
		this(filtering, filtering, wrap, wrap, wrap);
	}
	
}
