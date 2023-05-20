package com.greentree.engine.moon.modules.phase;

import org.springframework.beans.factory.annotation.Autowired;

import com.greentree.engine.moon.kernel.annotation.EngineBean;
import com.greentree.engine.moon.modules.ExitManager;
import com.greentree.engine.moon.modules.UpdateModule;

@EngineBean
public class UpdateEnginePhase implements EnginePhase<UpdateModule> {
	
	@Autowired
	private ExitManager exitManager;
	
	@Override
	public void run(Iterable<? extends UpdateModule> modules) {
		while(!exitManager.isShouldExit())
			for(var module : modules)
				module.update();
	}
	
	
}
