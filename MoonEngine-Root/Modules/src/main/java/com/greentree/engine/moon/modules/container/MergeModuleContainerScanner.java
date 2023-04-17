package com.greentree.engine.moon.modules.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

public class MergeModuleContainerScanner implements ModuleContainerBuilder {
	
	private final Collection<ModuleDefenition> modules = new ArrayList<>();
	
	@Override
	public ModuleContainerBuilder addModule(ModuleDefenition module) {
		modules.add(module);
		return this;
	}
	
	@Override
	public Stream<? extends ModuleDefenition> scan() {
		return modules.stream();
	}
	
}
