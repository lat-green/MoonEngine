package com.greentree.engine.moon.signals.device;

import com.greentree.engine.moon.module.EngineProperties;
import com.greentree.engine.moon.module.LaunchModule;
import com.greentree.engine.moon.module.TerminateModule;
import com.greentree.engine.moon.module.UpdateModule;
import com.greentree.engine.moon.module.annotation.CreateProperty;
import com.greentree.engine.moon.module.annotation.WriteProperty;
import com.greentree.engine.moon.signals.DevicesProperty;


public class DevicesModule implements LaunchModule, UpdateModule, TerminateModule {
	
	private DevicesProperty signals;
	
	@CreateProperty({DevicesProperty.class})
	@Override
	public void launch(EngineProperties context) {
		signals = new DevicesProperty();
		context.add(signals);
	}
	
	@Override
	public void terminate() {
		
	}
	
	@WriteProperty({DevicesProperty.class})
	@Override
	public void update() {
		signals.devices().update();
	}
	
}
