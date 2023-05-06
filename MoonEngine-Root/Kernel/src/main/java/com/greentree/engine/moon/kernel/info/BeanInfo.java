package com.greentree.engine.moon.kernel.info;


public interface BeanInfo {
	
	boolean isDependent(Class<?> cls);
	
	BeanInfo merge(BeanInfo info);
	
}
