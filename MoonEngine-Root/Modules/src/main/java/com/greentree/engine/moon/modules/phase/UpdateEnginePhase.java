package com.greentree.engine.moon.modules.phase;

import java.util.stream.StreamSupport;

import com.greentree.engine.moon.modules.EngineModule;
import com.greentree.engine.moon.modules.EngineProperties;
import com.greentree.engine.moon.modules.ExitManagerProperty;
import com.greentree.engine.moon.modules.UpdateModule;

public record UpdateEnginePhase(EngineProperties properties, MethodModuleSorter sorter)
		implements EnginePhase {
	
	public UpdateEnginePhase(EngineProperties properties) {
		this(properties, new AnnotatedMethodModuleSorter());
	}
	
	@Override
	public void run(Iterable<? extends EngineModule> modules) {
		var list = StreamSupport.stream(modules.spliterator(), false).filter(x -> x instanceof UpdateModule)
				.map(x -> (UpdateModule) x).collect(ArrayListCollector.INSTANCE());
		sorter.sort(list, "update");
		var manager = properties.get(ExitManagerProperty.class).manager();
		while(!manager.isShouldExit())
			for(var module : list)
				module.update();
	}
	
	
}
