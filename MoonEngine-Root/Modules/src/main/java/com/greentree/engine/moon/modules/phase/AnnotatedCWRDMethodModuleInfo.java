package com.greentree.engine.moon.modules.phase;

import com.greentree.engine.moon.modules.AnnotationUtil;
import com.greentree.engine.moon.modules.EngineModule;


public class AnnotatedCWRDMethodModuleInfo implements CWRDMethodModuleInfo {
	
	@Override
	public Iterable<Class<?>> getCreate(EngineModule module, String method) {
		return AnnotationUtil.getCreate(module, method);
	}
	
	@Override
	public Iterable<Class<?>> getWrite(EngineModule module, String method) {
		return AnnotationUtil.getWrite(module, method);
	}
	
	@Override
	public Iterable<Class<?>> getRead(EngineModule module, String method) {
		return AnnotationUtil.getRead(module, method);
	}
	
	@Override
	public Iterable<Class<?>> getDestroy(EngineModule module, String method) {
		return AnnotationUtil.getDestroy(module, method);
	}
	
}
