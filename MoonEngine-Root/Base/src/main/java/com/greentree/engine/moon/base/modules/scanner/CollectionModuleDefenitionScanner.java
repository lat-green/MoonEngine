package com.greentree.engine.moon.base.modules.scanner;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import com.greentree.engine.moon.modules.EngineModule;


public record CollectionModuleDefenitionScanner(Collection<? extends EngineModule> collection)
implements ModuleDefenitionScanner {
	
	public CollectionModuleDefenitionScanner(EngineModule... modules) {
		this(List.of(modules));
	}
	
	@Override
	public Stream<? extends ModuleDefenition> scan() {
		return collection.stream().map(ObjectModuleDefenition::new);
	}
	
}
