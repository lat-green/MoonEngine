package com.greentree.engine.moon.signals.device;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import com.greentree.engine.moon.signals.device.value.DeviceValue;

public class AbstractDevices implements DeviceCollection {
	private static final long serialVersionUID = 1L;
	
	protected final Map<Device<?>, DeviceValue> devices;
	
	public AbstractDevices() {
		devices = new HashMap<>();
	}
	
	public AbstractDevices(DeviceCollection signals) {
		this();
		for(var d : signals) {
			final var v = signals.getValue(d);
			devices.put(d, v);
		}
	}
	
	public void copyTo(AbstractDevices signals) {
		signals.devices.clear();
		signals.devices.putAll(devices);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		var other = (AbstractDevices) obj;
		return Objects.equals(devices, other.devices);
	}
	
	@Override
	public <V extends DeviceValue> void set(Device<V> device, V value) {
		devices.put(device, value);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <V extends DeviceValue> V getValue(Device<V> device) {
		if(devices.containsKey(device))
			return (V) devices.get(device);
		return device.defaultValue();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(devices);
	}
	
	@Override
	public String toString() {
		return "AbstractSignals [" + devices + "]";
	}
	
	@Override
	public Iterator<Device<?>> iterator() {
		return devices.keySet().iterator();
	}
	
}
