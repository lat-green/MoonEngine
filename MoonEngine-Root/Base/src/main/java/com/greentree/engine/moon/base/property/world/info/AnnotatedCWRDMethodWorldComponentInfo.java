package com.greentree.engine.moon.base.property.world.info;

import java.util.stream.Stream;

import com.greentree.engine.moon.base.component.UseStage;
import com.greentree.engine.moon.base.info.CWRDMethodInfo;


public class AnnotatedCWRDMethodWorldComponentInfo implements CWRDMethodInfo {
	
	@Override
	public Stream<? extends Class<?>> getCreate(Object object, String method) {
		return UseStage.CREATE.getWorldComponent(object, method);
	}
	
	@Override
	public Stream<? extends Class<?>> getWrite(Object object, String method) {
		return UseStage.WRITE.getWorldComponent(object, method);
	}
	
	@Override
	public Stream<? extends Class<?>> getRead(Object object, String method) {
		return UseStage.READ.getWorldComponent(object, method);
	}
	
	@Override
	public Stream<? extends Class<?>> getDestroy(Object object, String method) {
		return UseStage.DESTROY.getWorldComponent(object, method);
	}
	
}
