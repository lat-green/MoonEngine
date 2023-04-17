package com.greentree.engine.moon.modules.phase;

import com.greentree.engine.moon.modules.EngineModule;

public interface EnginePhase {
	
	void run(Iterable<? extends EngineModule> modules);
	
}
