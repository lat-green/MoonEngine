package com.greentree.engine.moon.signals;

import com.greentree.common.renderer.window.Key;
import com.greentree.commons.action.ListenerCloser;
import com.greentree.engine.moon.base.scene.EnginePropertiesWorldComponent;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.annotation.ReadWorldComponent;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.module.ExitManagerProperty;
import com.greentree.engine.moon.signals.device.EventState;
import com.greentree.engine.moon.signals.keyboard.KeyBoardButton;

public class ExitOnESCAPE implements InitSystem, DestroySystem {
	
	private ListenerCloser lc;
	
	@Override
	public void destroy() {
		if(lc != null) {
			lc.close();
			lc = null;
		}
	}
	
	@ReadWorldComponent({EnginePropertiesWorldComponent.class})
	@Override
	public void init(World world) {
		final var properties = world.get(EnginePropertiesWorldComponent.class).properties();
		final var w = properties.get(ExitManagerProperty.class).manager();
		lc = properties.get(DevicesProperty.class).devices()
				.addListener(new KeyBoardButton(Key.ESCAPE), EventState.ON_DISABLE, ()->
				{
					w.exit();
				});
	}
	
	
	
}
