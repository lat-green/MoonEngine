package com.greentree.engine.moon.render.shader.data;


public interface ShaderProgramData extends Iterable<ShaderData> {
	
	ShaderData vert();
	ShaderData frag();
	ShaderData geom();
	
}
