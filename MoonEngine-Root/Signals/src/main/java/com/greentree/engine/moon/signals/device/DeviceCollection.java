package com.greentree.engine.moon.signals.device;

import java.io.Serializable;

import com.greentree.engine.moon.signals.device.value.DeviceValue;
import com.greentree.engine.moon.signals.device.value.DeviceValue.Boolean;
import com.greentree.engine.moon.signals.device.value.DeviceValue.Float;

public interface DeviceCollection extends Iterable<Device<?>>, Serializable {
	
	<V extends DeviceValue> V getValue(Device<V> device);
	
	<V extends DeviceValue> void set(Device<V> device, V value);
	
	default void set(Device<? super DeviceValue.Boolean> device, boolean value) {
		set(device, new Boolean.Const(value));
	}
	
	default void set(Device<? super DeviceValue.Float> device, float value) {
		set(device, new Float.Const(value));
	}
	
}
