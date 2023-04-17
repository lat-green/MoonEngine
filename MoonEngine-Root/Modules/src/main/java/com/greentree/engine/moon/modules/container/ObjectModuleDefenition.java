package com.greentree.engine.moon.modules.container;

import com.greentree.engine.moon.modules.EngineModule;

public record ObjectModuleDefenition(EngineModule module) implements ModuleDefenition {
	
	@Override
	public EngineModule build() {
		return module;
	}
	
	
	
}
