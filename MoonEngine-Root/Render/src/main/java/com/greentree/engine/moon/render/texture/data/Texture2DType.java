package com.greentree.engine.moon.render.texture.data;

public record Texture2DType(Filtering filteringMin, Filtering filteringMag, Wrapping wrapX, Wrapping wrapY) {

		public static final Filtering DEFAULT_MIN_FILTERING = Filtering.LINEAR;
		public static final Filtering DEFAULT_MAG_FILTERING = Filtering.LINEAR;
		
		public static final Wrapping DEFAULT_X_WRAPPING = Wrapping.CLAMP_TO_EDGE; 
		public static final Wrapping DEFAULT_Y_WRAPPING = Wrapping.CLAMP_TO_EDGE;
		
		public Texture2DType(Filtering filteringMin, Filtering filteringMag, Wrapping wrap) {
			this(filteringMin, filteringMag, wrap, wrap);
		}
		public Texture2DType(Wrapping wrap) {
			this(DEFAULT_MIN_FILTERING, DEFAULT_MAG_FILTERING, wrap, wrap);
		}

		public Texture2DType() {
			this(DEFAULT_MIN_FILTERING, DEFAULT_MAG_FILTERING, DEFAULT_X_WRAPPING, DEFAULT_Y_WRAPPING);
		}

		public Texture2DType(Filtering filteringMin, Filtering filteringMag) {
			this(filteringMin, filteringMag, DEFAULT_X_WRAPPING, DEFAULT_Y_WRAPPING);
		}
		
		public Texture2DType(Filtering filtering, Wrapping wrap) {
			this(filtering, filtering, wrap, wrap);
		}
		
	}
