package com.greentree.engine.moon.modules.phase;

import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;

import com.greentree.engine.moon.kernel.annotation.EngineBean;
import com.greentree.engine.moon.modules.EngineModule;
import com.greentree.engine.moon.modules.TerminateModule;

@EngineBean
public final class TerminateEnginePhase implements EnginePhase {
	
	@Autowired
	private MethodModuleSorter sorter;
	
	@Override
	public void run(Iterable<? extends EngineModule> modules) {
		var list = StreamSupport.stream(modules.spliterator(), false).filter(x -> x instanceof TerminateModule)
				.map(x -> (TerminateModule) x).collect(ArrayListCollector.INSTANCE());
		sorter.sort(list, "terminate");
		for(var module : list)
			module.terminate();
		
	}
	
	
}
