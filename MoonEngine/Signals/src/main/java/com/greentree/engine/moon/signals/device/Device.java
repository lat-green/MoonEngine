package com.greentree.engine.moon.signals.device;

import java.io.Serializable;

import com.greentree.engine.moon.signals.device.value.DeviceValue;

public interface Device<V extends DeviceValue> extends Serializable {
	
	V defaultValue();
	
}
