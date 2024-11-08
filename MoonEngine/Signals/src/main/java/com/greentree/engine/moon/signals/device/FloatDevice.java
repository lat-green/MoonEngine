package com.greentree.engine.moon.signals.device;

import com.greentree.engine.moon.signals.device.value.DeviceValue;

public interface FloatDevice extends Device<DeviceValue.Float> {
	
	@Override
	default DeviceValue.Float defaultValue() {
		return new DeviceValue.Float.Const(0f);
	}
	
	default void signal(Devices signals, float value) {
		signals.set(this, value);
	}
	
}
