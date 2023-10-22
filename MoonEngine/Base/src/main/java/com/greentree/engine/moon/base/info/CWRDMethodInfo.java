package com.greentree.engine.moon.base.info;

import java.util.stream.Stream;

public interface CWRDMethodInfo {

	Stream<? extends Class<?>> getCreate(Object object, String method);
	Stream<? extends Class<?>> getWrite(Object object, String method);
	Stream<? extends Class<?>> getRead(Object object, String method);
	Stream<? extends Class<?>> getDestroy(Object object, String method);
	
}
