package com.greentree.engine.moon.signals;

import com.greentree.engine.moon.module.EngineProperties;
import com.greentree.engine.moon.module.LaunchModule;
import com.greentree.engine.moon.module.annotation.CreateProperty;


public final class CreateDevicesPropertyModule implements LaunchModule {
	
	
	@CreateProperty({DevicesProperty.class})
	@Override
	public void launch(EngineProperties context) {
		final var signals = new DevicesProperty();
		context.add(signals);
	}
	
}
