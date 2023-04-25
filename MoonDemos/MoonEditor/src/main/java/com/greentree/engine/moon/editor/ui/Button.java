package com.greentree.engine.moon.editor.ui;

import java.util.Objects;

import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.annotation.RequiredComponent;
import com.greentree.engine.moon.ecs.component.ConstComponent;

@RequiredComponent({Transform.class})
public final class Button implements ConstComponent {
	
	private static final long serialVersionUID = 1L;
	private final String name;
	
	public Button(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(!(obj instanceof Button other))
			return false;
		return Objects.equals(name, other.name);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
	
	public String name() {
		return name;
	}
	
}
