package com.greentree.engine.moon.modules.container;

import com.greentree.engine.moon.modules.EngineModule;

public interface ModuleContainerBuilder extends ModuleDefenitionScanner {
	
	ModuleContainerBuilder addModule(ModuleDefenition module);
	
	default ModuleContainerBuilder addModule(EngineModule module) {
		return addModule(new ObjectModuleDefenition(module));
	}

	
	default void addScanner(ModuleDefenitionScanner scanner) {
		scanner.scan().forEach(x -> addModule(x));
	}
}
