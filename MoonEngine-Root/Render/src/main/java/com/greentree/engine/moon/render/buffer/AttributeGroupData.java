package com.greentree.engine.moon.render.buffer;

import java.io.Serializable;

import com.greentree.commons.util.array.IntArray;

public record AttributeGroupData<VBO>(VBO vertex, IntArray sizes) implements Serializable {
	
}
