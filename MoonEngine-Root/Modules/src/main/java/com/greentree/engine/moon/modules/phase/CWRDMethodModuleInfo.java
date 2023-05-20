package com.greentree.engine.moon.modules.phase;

import com.greentree.engine.moon.modules.EngineModule;

public interface CWRDMethodModuleInfo {

	Iterable<Class<?>> getCreate(EngineModule module, String method);
	Iterable<Class<?>> getWrite(EngineModule module, String method);
	Iterable<Class<?>> getRead(EngineModule module, String method);
	Iterable<Class<?>> getDestroy(EngineModule module, String method);
	
}
