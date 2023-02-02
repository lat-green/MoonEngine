package com.greentree.engine.moon.render.shader;

import java.util.Iterator;
import java.util.Objects;

import com.greentree.commons.util.iterator.IteratorUtil;

public record ShaderProgramDataImpl(ShaderData vert, ShaderData frag, ShaderData geom)
		implements ShaderProgramData {
	
	public ShaderProgramDataImpl {
		Objects.requireNonNull(vert);
		Objects.requireNonNull(frag);
	}
	
	
	public ShaderProgramDataImpl(ShaderData vert, ShaderData frag) {
		this(vert, frag, null);
	}
	
	@Override
	public Iterator<ShaderData> iterator() {
		if(frag != null)
			return IteratorUtil.iterator(vert, frag, geom);
		return IteratorUtil.iterator(vert, frag);
	}
	
	
	
}
