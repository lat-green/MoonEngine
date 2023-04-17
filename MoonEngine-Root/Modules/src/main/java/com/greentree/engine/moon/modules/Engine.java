package com.greentree.engine.moon.modules;

import com.greentree.engine.moon.modules.container.CollectionModuleDefenitionScanner;
import com.greentree.engine.moon.modules.container.MergeModuleContainerScanner;
import com.greentree.engine.moon.modules.container.ServiceLoaderModuleDefenitionScanner;
import com.greentree.engine.moon.modules.phase.AnnotatedMethodModuleSorter;
import com.greentree.engine.moon.modules.phase.LaunchEnginePhase;

public final class Engine {
	
	private Engine() {
	}
	
	public static void launch(String[] args, EngineModule... modules) {
		
		var properties = new EnginePropertiesBase();
		
		var launch = new LaunchEnginePhase(properties, new AnnotatedMethodModuleSorter<>("launch"));
		
		var scanner = new MergeModuleContainerScanner();
		scanner.addScanner(new ServiceLoaderModuleDefenitionScanner());
		scanner.addScanner(new CollectionModuleDefenitionScanner(modules));
		
		var scanModules = scanner.scan().map(x -> x.build()).toList();
		
		launch.run(scanModules);
		
	}
	
}
