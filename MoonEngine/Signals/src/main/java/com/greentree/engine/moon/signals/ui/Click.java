package com.greentree.engine.moon.signals.ui;

import java.util.Objects;

import com.greentree.engine.moon.ecs.component.ConstComponent;
import com.greentree.engine.moon.signals.MouseButton;


public final class Click implements ConstComponent {
	
	private static final long serialVersionUID = 1L;
	private final float x, y;
	private final MouseButton button;
	
	public Click(float x, float y, MouseButton button) {
		this.x = x;
		this.y = y;
		this.button = button;
	}
	
	public MouseButton button() {
		return button;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(!(obj instanceof Click other))
			return false;
		return button == other.button && Float.floatToIntBits(x) == Float.floatToIntBits(other.x)
				&& Float.floatToIntBits(y) == Float.floatToIntBits(other.y);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(button, x, y);
	}
	
	@Override
	public String toString() {
		return "Click [" + x + ", " + y + ", " + button + "]";
	}
	
	public float x() {
		return x;
	}
	
	public float y() {
		return y;
	}
	
}
