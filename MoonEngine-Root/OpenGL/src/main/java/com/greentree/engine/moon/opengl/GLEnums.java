package com.greentree.engine.moon.opengl;

import com.greentree.common.graphics.sgl.enums.gl.GLShaderType;
import com.greentree.common.graphics.sgl.enums.gl.param.value.GLFiltering;
import com.greentree.common.graphics.sgl.enums.gl.param.value.GLWrapping;
import com.greentree.engine.moon.render.shader.ShaderType;
import com.greentree.engine.moon.render.texture.Filtering;
import com.greentree.engine.moon.render.texture.Wrapping;

public class GLEnums {
	
	public static GLShaderType get(ShaderType type) {
		return switch(type) {
			case VERTEX -> GLShaderType.VERTEX;
			case FRAGMENT -> GLShaderType.FRAGMENT;
			case GEOMETRY -> GLShaderType.GEOMETRY;
		};
	}
	
	public static GLFiltering get(Filtering filtering) {
		return switch(filtering) {
			case LINEAR -> GLFiltering.LINEAR;
			case NEAREST -> GLFiltering.NEAREST;
		
			default -> GLFiltering.valueOf(filtering.name());
		};
	}
	
	public static GLWrapping get(Wrapping wrap) {
		return switch(wrap) {
			case CLAMP_TO_BORDER -> GLWrapping.CLAMP_TO_BORDER;
			case CLAMP_TO_EDGE -> GLWrapping.CLAMP_TO_EDGE;
			case MIRRORED_REPEAT -> GLWrapping.MIRRORED_REPEAT;
			case REPEAT -> GLWrapping.REPEAT;
		};
	}
	
}
