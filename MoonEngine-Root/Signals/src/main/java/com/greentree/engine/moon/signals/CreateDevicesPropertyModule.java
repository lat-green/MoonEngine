package com.greentree.engine.moon.signals;

import com.greentree.engine.moon.modules.CreateProperty;
import com.greentree.engine.moon.modules.EngineProperties;
import com.greentree.engine.moon.modules.LaunchModule;


public final class CreateDevicesPropertyModule implements LaunchModule {
	
	
	@CreateProperty({DevicesProperty.class})
	@Override
	public void launch(EngineProperties context) {
		final var signals = new DevicesProperty();
		context.add(signals);
	}
	
}
