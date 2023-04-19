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
		var sum = 0F;
		for(var v : devices)
			sum += v.value();
		return sum;
	}
	
	@Override
	public boolean isConst() {
		for(var v : devices)
			if(!v.isConst())
				return false;
		return true;
	}
	
}

