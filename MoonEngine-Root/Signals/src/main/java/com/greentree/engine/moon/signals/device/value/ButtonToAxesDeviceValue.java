package com.greentree.engine.moon.signals.device.value;

public record ButtonToAxesDeviceValue(DeviceValue.Boolean bool) implements DeviceValue.Float {
	
	@Override
	public String toString() {
		return "ButtonToAxes [" + bool + "]";
	}
	
	@Override
	public float value() {
		return bool.value() ? 1 : 0;
	}
	
}
