package com.greentree.engine.moon.render.buffer;

import java.io.Serializable;
import java.util.Objects;

import com.greentree.commons.util.array.FloatArray;

public record FloatBuffer(FloatArray data, BufferUsing using) implements Serializable {
	
	public FloatBuffer {
		Objects.requireNonNull(data);
		Objects.requireNonNull(using);
	}
	
}
