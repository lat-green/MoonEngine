package com.greentree.engine.moon.render.shader;


public interface ShaderProgramData extends Iterable<ShaderData> {
	
	ShaderData vert();
	ShaderData frag();
	ShaderData geom();
	
}
