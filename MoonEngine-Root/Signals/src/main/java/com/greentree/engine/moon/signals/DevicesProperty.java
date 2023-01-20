package com.greentree.engine.moon.signals;

import com.greentree.engine.moon.module.EngineProperty;
import com.greentree.engine.moon.signals.device.Devices;


public record DevicesProperty(Devices devices) implements EngineProperty {
	
	public DevicesProperty {
		
	}
	
	public DevicesProperty() {
		this(new Devices());
	}
	
}
