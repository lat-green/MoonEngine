package com.greentree.engine.moon.signals.device.value;

import java.util.Objects;

public final class BorderValue implements DeviceValue.Float {
	
	private static final long serialVersionUID = 1L;
	
	private final Float base;
	private final float min, max;
	private float old_value, value;
	
	public BorderValue(Float base, float min, float max) {
		Objects.requireNonNull(base);
		this.base = base;
		this.min = min;
		this.max = max;
		old_value = base.value();
		value = base.value();
	}
	
	public BorderValue(Float base, float border) {
		this(base, -border, border);
	}
	
	@Override
	public float value() {
		var v = base.value();
		final var delta = v - old_value;
		old_value = v;
		final var new_value = value + delta;
		if(new_value > min && new_value < max)
			value = new_value;
		return value;
	}
	
}
