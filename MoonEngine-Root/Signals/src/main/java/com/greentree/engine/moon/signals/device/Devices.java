package com.greentree.engine.moon.signals.device;

import java.io.Serializable;
import java.util.function.Consumer;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.math.vector.Vector2f;
import com.greentree.commons.math.vector.Vector3f;
import com.greentree.engine.moon.signals.device.value.DeviceValue;
import com.greentree.engine.moon.signals.device.value.DeviceValue.Boolean;
import com.greentree.engine.moon.signals.device.value.DeviceValue.Float;

public interface Devices extends Iterable<Device<?>>, Serializable {
	
	<V extends DeviceValue> V getValue(Device<V> device);
	
	default boolean get(BooleanDevice device) {
		return getValue(device).value();
	}
	
	default float get(Device<? extends Float> device) {
		return getValue(device).value();
	}
	
	default Vector2f get(Device<? extends Float> x, Device<? extends Float> y) {
		return new Vector2f(get(x), get(y));
	}
	
	default Vector3f get(Device<? extends Float> x, Device<? extends Float> y, Device<? extends Float> z) {
		return new Vector3f(get(x), get(y), get(z));
	}
	
	<V extends DeviceValue> void set(Device<V> device, V value);
	
	default void set(Device<? super DeviceValue.Boolean> device, boolean value) {
		set(device, new Boolean.Const(value));
	}
	
	default void set(Device<? super DeviceValue.Float> device, float value) {
		set(device, new Float.Const(value));
	}
	
	default ListenerCloser onPress(Device<? extends Boolean> button, Runnable listener) {
		return opChange(button, v -> {
			if(v.value())
				listener.run();
		});
	}
	
	<V extends DeviceValue> ListenerCloser opChange(Device<? extends V> button, Consumer<? super V> listener);
	
}
