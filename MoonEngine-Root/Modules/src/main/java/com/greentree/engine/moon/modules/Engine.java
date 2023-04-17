package com.greentree.engine.moon.modules;

import com.greentree.engine.moon.modules.container.AnnotatedModuleSorter;
import com.greentree.engine.moon.modules.container.CollectionModuleDefenitionScanner;
import com.greentree.engine.moon.modules.container.ModuleContainerBuilderBase;
import com.greentree.engine.moon.modules.container.ServiceLoaderModuleDefenitionScanner;

public final class Engine {
	
	private Engine() {
	}
	
	public static void launch(String[] args, EngineModule... modules) {
		var builder = new ModuleContainerBuilderBase(new AnnotatedModuleSorter());
		
		builder.addScanner(new ServiceLoaderModuleDefenitionScanner());
		builder.addScanner(new CollectionModuleDefenitionScanner(modules));
		
		final var scenes = new EngineLoop(builder.build());
		scenes.run();
	}
	
}
