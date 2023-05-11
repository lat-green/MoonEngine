package com.greentree.engine.moon.modules;

import com.greentree.engine.moon.kernel.Kernel;
import com.greentree.engine.moon.kernel.annotation.BeanScan;
import com.greentree.engine.moon.modules.phase.LaunchEnginePhase;
import com.greentree.engine.moon.modules.phase.TerminateEnginePhase;
import com.greentree.engine.moon.modules.phase.UpdateEnginePhase;
import com.greentree.engine.moon.modules.scanner.CollectionModuleDefenitionScanner;
import com.greentree.engine.moon.modules.scanner.ConfigModuleContainerScanner;
import com.greentree.engine.moon.modules.scanner.ModuleDefenition;
import com.greentree.engine.moon.modules.scanner.ServiceLoaderModuleDefenitionScanner;
import com.greentree.engine.moon.modules.scanner.SpringModuleDefenitionScanner;

@BeanScan
public final class Engine {
	private Engine() {
	}
	
	public static void launch(String[] args, EngineModule... modules) {
		var context = Kernel.launch(args);
		
		var properties = new EnginePropertiesBase();
		
		var launch = new LaunchEnginePhase(properties);
		var update = new UpdateEnginePhase(properties);
		var terminate = new TerminateEnginePhase();
		
		var scanner = new ConfigModuleContainerScanner();
		scanner.addScanner(context.getBean(SpringModuleDefenitionScanner.class));
		scanner.addScanner(new ServiceLoaderModuleDefenitionScanner());
		scanner.addScanner(new CollectionModuleDefenitionScanner(modules));
		
		var scanModules = scanner.scan().map(ModuleDefenition::build).toList();
		
		launch.run(scanModules);
		update.run(scanModules);
		terminate.run(scanModules);
	}
	
}
