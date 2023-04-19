package com.greentree.engine.moon.signals.device;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.function.Consumer;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.observer.type.TypedObjectAction;
import com.greentree.engine.moon.signals.device.value.DeviceValue;

public final class Devices extends AbstractDevices implements Externalizable {
	
	private static final long serialVersionUID = 1L;
	
	private transient final TypedObjectAction<Device<?>, DeviceValue> action = new TypedObjectAction<>();
	
	public <V extends DeviceValue> ListenerCloser addListener(BooleanDevice button,
			EventState state, Runnable listener) {
		return addListener(button, v -> {
			final var current = getValue(button).value();
			final var next = v.value();
			if(state.is(current, next))
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
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
	}
	
	@Override
	public <V extends DeviceValue> void set(Device<V> device, V value) {
		action.event(device, value);
		super.set(device, value);
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
	}
	
	void update() {
		for(var d : devices.keySet())
			action.event(d, getValue(d));
	}
	
}
