package com.greentree.engine.moon.signals.device;

import java.util.function.Consumer;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.observer.type.TypedObjectAction;
import com.greentree.engine.moon.signals.device.value.DeviceValue;

public final class Devices extends AbstractDevices {
	
	private static final long serialVersionUID = 1L;
	
	private final TypedObjectAction<Device<?>, DeviceValue> action = new TypedObjectAction<>();
	
	
	public <V extends DeviceValue> ListenerCloser onPress(BooleanDevice button, Runnable listener) {
		return addListener(button, v -> {
			if(v.value())
				listener.run();
		});
	}
	
	@SuppressWarnings("unchecked")
	public <V extends DeviceValue> ListenerCloser addListener(Device<V> device,
			Consumer<? super V> listener) {
		listener.accept(getValue(device));
		return action.addListener(device, (Consumer<? super DeviceValue>) listener);
	}
	
	@Override
	public <V extends DeviceValue> void set(Device<V> device, V value) {
		action.event(device, value);
		super.set(device, value);
	}
	
}
