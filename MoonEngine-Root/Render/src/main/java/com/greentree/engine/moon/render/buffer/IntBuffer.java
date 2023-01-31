package com.greentree.engine.moon.render.buffer;

import java.io.Serializable;
import java.util.Objects;

import com.greentree.commons.util.array.IntArray;

public record IntBuffer(IntArray data, BufferUsing using) implements Serializable {
	
	public IntBuffer {
		Objects.requireNonNull(data);
		Objects.requireNonNull(using);
	}
	
	
	
}
