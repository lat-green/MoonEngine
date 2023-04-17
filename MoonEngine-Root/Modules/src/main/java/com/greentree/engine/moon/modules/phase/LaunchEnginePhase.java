package com.greentree.engine.moon.modules.phase;

import java.util.stream.StreamSupport;

import com.greentree.engine.moon.modules.EngineModule;
import com.greentree.engine.moon.modules.EngineProperties;
import com.greentree.engine.moon.modules.LaunchModule;

public record LaunchEnginePhase(EngineProperties properties, MethodModuleSorter sorter)
		implements EnginePhase {
	
	
	public LaunchEnginePhase(EngineProperties properties) {
		this(properties, AnnotatedMethodModuleSorter.INSTANCE);
	}
	
	@Override
	public void run(Iterable<? extends EngineModule> modules) {
		var list = StreamSupport.stream(modules.spliterator(), false).filter(x -> x instanceof LaunchModule)
				.map(x -> (LaunchModule) x).collect(ArrayListCollector.INSTANCE());
		sorter.sort(list, "launch");
		for(var module : list)
			module.launch(properties);
		
	}
	
	
}
