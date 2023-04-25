package com.greentree.engine.moon.editor.ui;

import java.util.Objects;

import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.ecs.component.ConstComponent;


public final class ButtonClick implements ConstComponent {
	
	private static final long serialVersionUID = 1L;
	private final Entity button;
	
	public ButtonClick(Entity button) {
		this.button = button;
	}
	
	public Entity button() {
		return button;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(!(obj instanceof ButtonClick other))
			return false;
		return Objects.equals(button, other.button);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(button);
	}
	
	@Override
	public String toString() {
		return "ButtonClick [" + button + "]";
	}
	
	
}
