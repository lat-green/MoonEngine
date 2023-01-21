package com.greentree.engine.moon.signals.device;

import java.util.Objects;

import com.greentree.engine.moon.ecs.component.Component;

public record DevicesComponent(AbstractDevices signals) implements Component {
	
	public DevicesComponent() {
		this(new AbstractDevices());
	}
	
	public DevicesComponent {
		Objects.requireNonNull(signals);
	}
	
	@Override
	public DevicesComponent copy() {
		return new DevicesComponent(new AbstractDevices(signals));
	}
	
	@Override
	public boolean copyTo(Component other) {
		final var sc = (DevicesComponent) other;
		signals.copyTo(sc.signals);
		return true;
	}
	
	@Override
	public String toString() {
		return "SignalsComponent [" + signals + "]";
	}
	
}
