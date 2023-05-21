package com.greentree.engine.moon.demo1;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.engine.moon.base.modules.ReadProperty;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.TerminateModule;
import com.greentree.engine.moon.modules.property.EngineProperties;
import com.greentree.engine.moon.modules.property.ExitManagerProperty;
import com.greentree.engine.moon.signals.DevicesProperty;
import com.greentree.engine.moon.signals.Key;
import com.greentree.engine.moon.signals.device.Devices;

public class ExitOnESCAPE implements LaunchModule, TerminateModule {
	
	private Devices devices;
	
	private ListenerCloser lc;
	
	@Override
	public void terminate() {
		if(lc != null) {
			lc.close();
			lc = null;
		}
	}
	
	@ReadProperty({DevicesProperty.class})
	@Override
	public void launch(EngineProperties properties) {
		devices = properties.get(DevicesProperty.class).devices();
		lc = devices
				.onPress(Key.ESCAPE, () ->
				{
					final var w = properties.get(ExitManagerProperty.class).manager();
					w.exit();
				});
	}
	
}
