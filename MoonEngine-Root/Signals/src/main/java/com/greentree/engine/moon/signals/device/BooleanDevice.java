package com.greentree.engine.moon.signals.device;

import com.greentree.engine.moon.signals.device.value.DeviceValue;

public interface BooleanDevice extends Device<DeviceValue.Boolean> {
	
	@Override
	default DeviceValue.Boolean defaultValue() {
		return new DeviceValue.Boolean.Const(false);
	}
	
	default void press(DeviceCollection signals) {
		signals.set(this, new DeviceValue.Boolean.Const(true));
	}
	
	default void release(DeviceCollection signals) {
		signals.set(this, new DeviceValue.Boolean.Const(false));
	}
	
}
