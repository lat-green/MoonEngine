package com.greentree.engine.moon.modules.phase;

import java.util.stream.StreamSupport;

import com.greentree.engine.moon.modules.EngineModule;
import com.greentree.engine.moon.modules.TerminateModule;

public record TerminateEnginePhase(MethodModuleSorter sorter)
		implements EnginePhase {
	
	public TerminateEnginePhase() {
		this(AnnotatedMethodModuleSorter.INSTANCE);
	}
	@Override
	public void run(Iterable<? extends EngineModule> modules) {
		var list = StreamSupport.stream(modules.spliterator(), false).filter(x -> x instanceof TerminateModule)
				.map(x -> (TerminateModule) x).collect(ArrayListCollector.INSTANCE());
		sorter.sort(list, "terminate");
		for(var module : list)
			module.terminate();
		
	}
	
	
}
