package com.greentree.engine.moon.signals.device.value;

import java.util.Objects;

import com.greentree.commons.util.iterator.IteratorUtil;

public record SumDeviceValue(Iterable<? extends DeviceValue.Float> devices)
		implements DeviceValue.Float {
	
	public SumDeviceValue {
		Objects.requireNonNull(devices);
	}
	
	@SafeVarargs
	public SumDeviceValue(DeviceValue.Float... devices) {
		this(IteratorUtil.iterable(devices));
	}
	
	@Override
	public float value() {
		float sum = 0;
		for(var v : devices)
			sum += v.value();
		return sum;
	}
	
}

