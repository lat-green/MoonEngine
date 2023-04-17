package com.greentree.engine.moon.modules;

import com.greentree.engine.moon.modules.base.EngineLoop;
import com.greentree.engine.moon.modules.container.AnnotatedModuleContainerBuilder;
import com.greentree.engine.moon.modules.container.CollectionEngineModuleDefenitionScanner;
import com.greentree.engine.moon.modules.container.ServiceLoaderEngineModuleDefenitionScanner;

public final class Engine {
	
	private Engine() {
	}
	
	public static void launch(String[] args, EngineModule... modules) {
		var builder = new AnnotatedModuleContainerBuilder();
		
		builder.addScanner(new ServiceLoaderEngineModuleDefenitionScanner());
		builder.addScanner(new CollectionEngineModuleDefenitionScanner(modules));
		
		final var scenes = new EngineLoop(builder.build());
		scenes.run();
	}
	
}
