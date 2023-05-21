package com.greentree.engine.moon.modules.phase;

import com.greentree.engine.moon.modules.EngineProperties;
import com.greentree.engine.moon.modules.ExitManager;
import com.greentree.engine.moon.modules.ExitManagerProperty;
import com.greentree.engine.moon.modules.UpdateModule;

public record UpdateEnginePhase(ExitManager exitManager) implements EnginePhase<UpdateModule> {
	
	
	public UpdateEnginePhase(EngineProperties properties) {
		this(properties.get(ExitManagerProperty.class).manager());
	}
	
	@Override
	public void run(Iterable<? extends UpdateModule> modules) {
		while(!exitManager.isShouldExit())
			for(var module : modules)
				module.update();
	}
	
	
}
