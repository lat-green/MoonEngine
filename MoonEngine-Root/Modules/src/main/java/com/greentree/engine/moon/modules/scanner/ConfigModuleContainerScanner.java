package com.greentree.engine.moon.modules.scanner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import com.greentree.engine.moon.modules.EngineModule;

public class ConfigModuleContainerScanner implements ModuleDefenitionScanner {
	
	private final Collection<ModuleDefenition> modules = new ArrayList<>();
	
	public ConfigModuleContainerScanner addModule(ModuleDefenition module) {
		modules.add(module);
		return this;
	}
	
	@Override
	public Stream<? extends ModuleDefenition> scan() {
		return modules.stream();
	}
	
	
	public ConfigModuleContainerScanner addModule(EngineModule module) {
		return addModule(new ObjectModuleDefenition(module));
	}
	
	public void addScanner(ModuleDefenitionScanner scanner) {
		scanner.scan().forEach(this::addModule);
	}
	
}
