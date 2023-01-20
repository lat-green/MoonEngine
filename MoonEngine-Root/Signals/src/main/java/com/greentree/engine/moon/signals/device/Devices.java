package com.greentree.engine.moon.signals.device;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.observer.type.TypedObjectAction;
import com.greentree.engine.moon.signals.device.value.DeviceValue;
import com.greentree.engine.moon.signals.device.value.DeviceValue.Boolean;

public final class Devices extends AbstractDevices implements Externalizable {
	
	private static final long serialVersionUID = 1L;
	
	private transient final TypedObjectAction<Device<?>, DeviceValue> action = new TypedObjectAction<>();
	private transient final TypedObjectAction<Device<?>, DeviceValue> tickAction = new TypedObjectAction<>();
	
	public <V extends DeviceValue> ListenerCloser addListener(BooleanDevice button,
			EventState state, Runnable listener) {
		return addListener(button, state, Boolean::value, listener);
	}
	
	@SuppressWarnings("unchecked")
	public <V extends DeviceValue> ListenerCloser addListener(Device<V> device,
			Consumer<? super V> listener) {
		listener.accept(getValue(device));
		return action.addListener(device, (Consumer<? super DeviceValue>) listener);
	}
	
	public <V extends DeviceValue> ListenerCloser addListener(Device<V> device, EventState state,
			Predicate<? super V> predicate, Runnable listener) {
		return addListener(device, v-> {
			final var current = predicate.test(getValue(device));
			final var next = predicate.test(v);
			if(state.is(current, next))
				listener.run();
		});
	}
	
	@SuppressWarnings("unchecked")
	public <V extends DeviceValue> ListenerCloser addTickListener(Device<V> device,
			Consumer<? super V> listener) {
		listener.accept(getValue(device));
		return tickAction.addListener(device, (Consumer<? super DeviceValue>) listener);
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
			tickAction.event(d, getValue(d));
	}
	
}
