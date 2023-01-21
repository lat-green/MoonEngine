package com.greentree.engine.moon.ecs.component;

import java.util.Objects;

/** @deprecated use ConstComponent with empty record
 * @author arseny */
@Deprecated
public abstract class TagComponent implements ConstComponent {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj == this) return true;
		return Objects.equals(getClass(), obj.getClass());
	}
	
}
