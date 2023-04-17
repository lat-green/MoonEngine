package com.greentree.engine.moon.modules.phase;

import com.greentree.engine.moon.modules.EngineModule;

public interface EnginePhase<T extends EngineModule> {
	
	void run(Iterable<? extends T> modules);

}
