package com.greentree.engine.moon.render.shader.data;

import java.util.Iterator;
import java.util.Objects;

import com.greentree.commons.util.iterator.IteratorUtil;

public record ShaderProgramDataImpl(ShaderData vert, ShaderData frag, ShaderData geom) implements ShaderProgramData {
	
	public ShaderProgramDataImpl {
		Objects.requireNonNull(vert);
		Objects.requireNonNull(frag);
	}
	
	@Override
	public Iterator<ShaderData> iterator() {
		if(frag != null)
			return IteratorUtil.iterator(vert, frag, geom);
		return IteratorUtil.iterator(vert, frag);
	}
	
	
	
}
