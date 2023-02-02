package com.greentree.engine.moon.render.shader;

import java.util.HashSet;

import com.greentree.commons.util.iterator.IteratorUtil;

public enum ShaderType {
	
	VERTEX(true),
	FRAGMENT(true),
	GEOMETRY(false),
	;

	public final boolean required;
	
	private ShaderType(boolean required) {
		this.required = required;
	}

	public static Iterable<ShaderType> required() {
		return IteratorUtil.filter(IteratorUtil.iterable(values()), t -> t.required);
	}

	public static void check(Iterable<? extends ShaderData> shaders) {
		final var types = new HashSet<ShaderType>();
		for(var s : shaders) {
			final var type = s.type();
			if(types.contains(type))
				throw new IllegalArgumentException("double add " + type);
			types.add(type);
		}
		for(var type : ShaderType.required())
			if(!types.contains(type))
				throw new IllegalArgumentException("not add required type " + type);
	}

}
