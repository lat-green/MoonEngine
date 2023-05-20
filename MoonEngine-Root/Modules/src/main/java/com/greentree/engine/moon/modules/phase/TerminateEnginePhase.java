package com.greentree.engine.moon.modules.phase;

import com.greentree.engine.moon.kernel.annotation.EngineBean;
import com.greentree.engine.moon.modules.TerminateModule;

@EngineBean
public final class TerminateEnginePhase implements EnginePhase<TerminateModule> {
	
	@Override
	public void run(Iterable<? extends TerminateModule> modules) {
		for(var module : modules)
			module.terminate();
		
	}
	
	
}
