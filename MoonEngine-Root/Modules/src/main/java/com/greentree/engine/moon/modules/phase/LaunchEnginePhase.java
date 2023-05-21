package com.greentree.engine.moon.modules.phase;

import com.greentree.engine.moon.modules.EngineProperties;
import com.greentree.engine.moon.modules.LaunchModule;

public record LaunchEnginePhase(EngineProperties properties) implements EnginePhase<LaunchModule> {
	
	@Override
	public void run(Iterable<? extends LaunchModule> modules) {
		for(var module : modules) {
			module.launch(properties);
		}
		
	}
	
	
}
