package com.greentree.engine.moon.signals;

import com.greentree.engine.moon.base.property.modules.CreateProperty;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.property.EngineProperties;


public final class CreateDevicesPropertyModule implements LaunchModule {
	
	
	@CreateProperty({DevicesProperty.class})
	@Override
	public void launch(EngineProperties context) {
		context.add(new DevicesProperty());
	}
	
}
