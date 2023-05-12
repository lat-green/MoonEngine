package com.greentree.engine.moon.demo1;

import org.springframework.beans.factory.annotation.Autowired;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.engine.moon.kernel.annotation.EngineBean;
import com.greentree.engine.moon.modules.EngineProperties;
import com.greentree.engine.moon.modules.ExitManagerProperty;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.ReadProperty;
import com.greentree.engine.moon.modules.TerminateModule;
import com.greentree.engine.moon.signals.DevicesProperty;
import com.greentree.engine.moon.signals.Key;
import com.greentree.engine.moon.signals.device.Devices;

@EngineBean
public class ExitOnESCAPE implements LaunchModule, TerminateModule {
	
	@Autowired(required = false)
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
		lc = devices
				.onPress(Key.ESCAPE, () ->
				{
					final var w = properties.get(ExitManagerProperty.class).manager();
					w.exit();
				});
	}
	
}
