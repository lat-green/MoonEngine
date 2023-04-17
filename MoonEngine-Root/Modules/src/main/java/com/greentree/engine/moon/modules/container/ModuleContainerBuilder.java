package com.greentree.engine.moon.modules.container;

public interface ModuleContainerBuilder {
	
	ModuleContainerBuilder addModule(EngineModuleDefenition module);
	
	ModuleContainer build();

	
	default void addScanner(EngineModuleDefenitionScanner scanner) {
		scanner.scan().forEach(x -> addModule(x));
	}
}
