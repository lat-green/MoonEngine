package com.greentree.engine.moon.render.shader;

import java.util.Objects;

/** @author arseny */
public record ShaderDataImpl(String text, ShaderType type) implements ShaderData {
	
	public ShaderDataImpl {
		Objects.requireNonNull(text);
		Objects.requireNonNull(type);
	}
	
}
