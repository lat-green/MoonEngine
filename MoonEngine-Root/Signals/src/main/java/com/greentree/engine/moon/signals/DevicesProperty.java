package com.greentree.engine.moon.signals;

import com.greentree.engine.moon.ecs.WorldComponent;
import com.greentree.engine.moon.modules.EngineProperty;
import com.greentree.engine.moon.signals.device.Devices;


public record DevicesProperty(Devices devices) implements EngineProperty, WorldComponent {
	
	public DevicesProperty {
		
	}
	
	public DevicesProperty() {
		this(new Devices());
	}
	
}
