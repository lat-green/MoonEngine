package com.greentree.engine.moon.signals.device;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.observer.type.TypedObjectAction;
import com.greentree.engine.moon.signals.device.value.DeviceValue;

public class HashMapDevices implements Devices {
	private static final long serialVersionUID = 1L;
	
	protected final Map<Device<?>, DeviceValue> values;
	
	private final TypedObjectAction<Device<?>, DeviceValue> action = new TypedObjectAction<>();
	
	public HashMapDevices() {
		values = new HashMap<>();
	}
	
	public HashMapDevices(Devices signals) {
		this();
		for(var d : signals) {
			final var v = signals.getValue(d);
			values.put(d, v);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		var other = (HashMapDevices) obj;
		return Objects.equals(values, other.values);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <V extends DeviceValue> V getValue(Device<V> device) {
		if(values.containsKey(device))
			return (V) values.get(device);
		return device.defaultValue();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(values);
	}
	
	@Override
	public Iterator<Device<?>> iterator() {
		return values.keySet().iterator();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <V extends DeviceValue> ListenerCloser opChange(Device<? extends V> button, Consumer<? super V> listener) {
		listener.accept(getValue(button));
		return action.addListener(button, (Consumer<? super DeviceValue>) listener);
	}
	
	@Override
	public <V extends DeviceValue> void set(Device<V> device, V value) {
		action.event(device, value);
		values.put(device, value);
	}
	
	@Override
	public String toString() {
		return "AbstractSignals [" + values + "]";
	}
	
}
