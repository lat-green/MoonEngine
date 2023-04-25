package com.greentree.engine.moon.editor;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.engine.moon.modules.EngineProperties;
import com.greentree.engine.moon.modules.ExitManagerProperty;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.ReadProperty;
import com.greentree.engine.moon.modules.TerminateModule;
import com.greentree.engine.moon.signals.DevicesProperty;
import com.greentree.engine.moon.signals.Key;
import com.greentree.engine.moon.signals.device.EventState;
import com.greentree.engine.moon.signals.keyboard.KeyBoardButton;

public class ExitOnESCAPE implements LaunchModule, TerminateModule {
	
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
		lc = properties.get(DevicesProperty.class).devices()
				.addListener(new KeyBoardButton(Key.ESCAPE), EventState.ON_ENABLE, () ->
				{
					final var w = properties.get(ExitManagerProperty.class).manager();
					w.exit();
				});
	}
	
}
