package com.greentree.engine.moon.render.shader.data;

import java.io.Serializable;

public interface ShaderData extends Serializable {
	
	String text();
	ShaderType type();
	
}
