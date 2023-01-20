package com.greentree.engine.moon.signals.device.value;

import java.util.Objects;

import com.greentree.commons.util.iterator.IteratorUtil;

public record MultDeviceValue(Iterable<? extends DeviceValue.Float> devices)
		implements DeviceValue.Float {
	
	public MultDeviceValue {
		Objects.requireNonNull(devices);
	}
	
	@SafeVarargs
	public MultDeviceValue(DeviceValue.Float... devices) {
		this(IteratorUtil.iterable(devices));
	}
	
	@Override
	public float value() {
		float result = 1;
		for(var v : devices)
			result *= v.value();
		return result;
	}
	
}
