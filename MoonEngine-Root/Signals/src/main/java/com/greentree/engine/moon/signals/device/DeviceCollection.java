package com.greentree.engine.moon.signals.device;

import java.io.Serializable;

import com.greentree.commons.math.vector.Vector2f;
import com.greentree.commons.math.vector.Vector3f;
import com.greentree.engine.moon.signals.device.value.DeviceValue;
import com.greentree.engine.moon.signals.device.value.DeviceValue.Boolean;
import com.greentree.engine.moon.signals.device.value.DeviceValue.Float;

public interface DeviceCollection extends Iterable<Device<?>>, Serializable {
	
	<V extends DeviceValue> V getValue(Device<V> device);
	
	default boolean get(BooleanDevice device) {
		return getValue(device).value();
	}
	
	default float get(FloatDevice device) {
		return getValue(device).value();
	}
	
	default Vector2f get(FloatDevice x, FloatDevice y) {
		return new Vector2f(get(x), get(y));
	}
	
	default Vector3f get(FloatDevice x, FloatDevice y, FloatDevice z) {
		return new Vector3f(get(x), get(y), get(z));
	}
	
	<V extends DeviceValue> void set(Device<V> device, V value);
	
	default void set(Device<? super DeviceValue.Boolean> device, boolean value) {
		set(device, new Boolean.Const(value));
	}
	
	default void set(Device<? super DeviceValue.Float> device, float value) {
		set(device, new Float.Const(value));
	}
	
}
