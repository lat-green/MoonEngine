package com.greentree.engine.moon.base.modules.scanner;

import java.util.ServiceLoader;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.greentree.engine.moon.modules.EngineModule;


public class ServiceLoaderModuleDefenitionScanner implements ModuleDefenitionScanner {
	
	@Override
	public Stream<ModuleDefenition> scan() {
		return StreamSupport.stream(ServiceLoader.load(EngineModule.class).spliterator(), false)
				.map(ObjectModuleDefenition::new);
	}
	
}
