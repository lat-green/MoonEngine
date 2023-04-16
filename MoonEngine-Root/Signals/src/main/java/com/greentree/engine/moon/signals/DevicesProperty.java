package com.greentree.engine.moon.signals;

import com.greentree.engine.moon.modules.EngineProperty;
import com.greentree.engine.moon.signals.device.Devices;


public record DevicesProperty(Devices devices) implements EngineProperty {
	
	public DevicesProperty {
		
	}
	
	public DevicesProperty() {
		this(new Devices());
	}
	
}
