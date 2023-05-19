package com.greentree.engine.moon.modules.phase;

import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;

import com.greentree.engine.moon.kernel.annotation.EngineBean;
import com.greentree.engine.moon.modules.EngineModule;
import com.greentree.engine.moon.modules.ExitManager;
import com.greentree.engine.moon.modules.UpdateModule;

@EngineBean
public class UpdateEnginePhase implements EnginePhase {
	
	@Autowired
	private MethodModuleSorter sorter;
	@Autowired
	private ExitManager exitManager;
	
	@Override
	public void run(Iterable<? extends EngineModule> modules) {
		var list = StreamSupport.stream(modules.spliterator(), false).filter(x -> x instanceof UpdateModule)
				.map(x -> (UpdateModule) x).collect(ArrayListCollector.INSTANCE());
		sorter.sort(list, "update");
		while(!exitManager.isShouldExit())
			for(var module : list)
				module.update();
	}
	
	
}
