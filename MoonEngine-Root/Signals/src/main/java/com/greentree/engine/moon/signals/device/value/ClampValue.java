package com.greentree.engine.moon.signals.device.value;

import java.util.Objects;

import com.greentree.commons.math.Mathf;

public record ClampValue(Float base, float min, float max) implements DeviceValue.Float {
	
	public ClampValue {
		Objects.requireNonNull(base);
	}
	
	public ClampValue(Float base, float border) {
		this(base, -border, border);
	}
	
	@Override
	public float value() {
		var v = base.value();
		v = Mathf.clamp(min, max, v);
		return v;
	}
	
}
