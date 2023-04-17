package com.greentree.engine.moon.modules.phase;

import java.util.List;
import java.util.stream.StreamSupport;

import com.greentree.engine.moon.modules.EngineModule;
import com.greentree.engine.moon.modules.EngineProperties;
import com.greentree.engine.moon.modules.LaunchModule;

public record LaunchEnginePhase(EngineProperties properties, ModuleSorter<? super LaunchModule> sorter)
		implements EnginePhase<LaunchModule> {
	
	@Override
	public void run(Iterable<? extends LaunchModule> modules) {
		var list = StreamSupport.stream(modules.spliterator(), false).toList();
		sorter.sort(list);
		for(var module : list)
			module.launch(properties);
	}

	d void run(List<EngineModule> scanModules) {
		// TODO Auto-generated method stub
		
	}
	
}
