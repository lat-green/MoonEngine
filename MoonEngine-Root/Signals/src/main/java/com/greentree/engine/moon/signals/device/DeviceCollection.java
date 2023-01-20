package com.greentree.engine.moon.signals.device;

import java.io.Serializable;

import com.greentree.engine.moon.signals.device.value.DeviceValue;

public interface DeviceCollection extends Iterable<Device<?>>, Serializable {
	
	<V extends DeviceValue> V getValue(Device<V> device);
	
	<V extends DeviceValue> void set(Device<V> device, V value);
	
}
