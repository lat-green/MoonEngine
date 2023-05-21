package com.greentree.engine.moon.base.modules.info;

import java.util.stream.Stream;

import com.greentree.engine.moon.modules.EngineModule;

public interface CWRDMethodModuleInfo {

	Stream<Class<?>> getCreate(EngineModule module, String method);
	Stream<Class<?>> getWrite(EngineModule module, String method);
	Stream<Class<?>> getRead(EngineModule module, String method);
	Stream<Class<?>> getDestroy(EngineModule module, String method);
	
}
