package com.greentree.engine.moon.modules.container;

public interface ModuleContainerBuilder {
	
	ModuleContainerBuilder addModule(EngineModuleDefenition module);
	
	ModuleContainer build();
	
}
