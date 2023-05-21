package com.greentree.engine.moon.modules.phase;

import com.greentree.engine.moon.modules.TerminateModule;

public final class TerminateEnginePhase implements EnginePhase<TerminateModule> {
	
	@Override
	public void run(Iterable<? extends TerminateModule> modules) {
		for(var module : modules)
			module.terminate();
		
	}
	
	
}
