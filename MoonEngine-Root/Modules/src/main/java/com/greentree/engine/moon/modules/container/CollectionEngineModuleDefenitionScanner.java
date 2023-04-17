package com.greentree.engine.moon.modules.container;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import com.greentree.engine.moon.modules.EngineModule;


public record CollectionEngineModuleDefenitionScanner(Collection<? extends EngineModule> collection)
implements EngineModuleDefenitionScanner {
	
	public CollectionEngineModuleDefenitionScanner(EngineModule... modules) {
		this(List.of(modules));
	}
	
	@Override
	public Stream<? extends EngineModuleDefenition> scan() {
		return collection.stream().map(ObjectEngineModuleDefenition::new);
	}
	
}
