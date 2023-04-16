package com.greentree.engine.moon.signals.device;

import com.greentree.engine.moon.modules.EngineProperties;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.ReadProperty;
import com.greentree.engine.moon.modules.UpdateModule;
import com.greentree.engine.moon.modules.WriteProperty;
import com.greentree.engine.moon.signals.DevicesProperty;


public final class UpdateDevicesPropertyModule implements LaunchModule, UpdateModule {
	
	private DevicesProperty signals;
	
	@ReadProperty({DevicesProperty.class})
	@Override
	public void launch(EngineProperties context) {
		signals = context.get(DevicesProperty.class);
	}
	
	@WriteProperty({DevicesProperty.class})
	@Override
	public void update() {
		signals.devices().update();
	}
	
}
