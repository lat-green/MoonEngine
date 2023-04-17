package com.greentree.engine.moon.modules.container;

import java.util.Collection;
import java.util.stream.Stream;


public record CollectionEngineModuleDefenitionScanner(Collection<? extends EngineModuleDefenition> collection)
		implements EngineModuleDefenitionScanner {
	
	@Override
	public Stream<? extends EngineModuleDefenition> scan() {
		return collection.stream();
	}
	
}
