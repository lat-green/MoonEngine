package com.greentree.engine.moon.signals.device;

import com.greentree.engine.moon.signals.device.value.DeviceValue;

public interface BooleanDevice extends Device<DeviceValue.Boolean> {
	
	@Override
	default DeviceValue.Boolean defaultValue() {
		return new DeviceValue.Boolean.Const(false);
	}
	
	default void press(Devices signals) {
		signals.set(this, true);
	}
	
	default void release(Devices signals) {
		signals.set(this, false);
	}
	
}
