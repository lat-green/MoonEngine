package com.greentree.engine.moon.modules.container;

import java.util.ServiceLoader;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.greentree.engine.moon.modules.EngineModule;


public class ServiceLoaderEngineModuleDefenitionScanner implements EngineModuleDefenitionScanner {
	
	@Override
	public Stream<EngineModuleDefenition> scan() {
		return StreamSupport.stream(ServiceLoader.load(EngineModule.class).spliterator(), false)
				.map(x -> new ObjectEngineModuleDefenition(x));
	}
	
}
