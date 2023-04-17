package com.greentree.engine.moon.modules.container;

import com.greentree.engine.moon.modules.EngineModule;

public record ObjectEngineModuleDefenition(EngineModule module) implements EngineModuleDefenition {
	
	@Override
	public EngineModule build() {
		return module;
	}
	
	
	
}
