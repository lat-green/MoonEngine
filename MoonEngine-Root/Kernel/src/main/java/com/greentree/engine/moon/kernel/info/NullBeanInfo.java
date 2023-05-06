package com.greentree.engine.moon.kernel.info;


public final class NullBeanInfo implements BeanInfo {
	
	@Override
	public BeanInfo merge(BeanInfo info) {
		return info;
	}
	
	@Override
	public boolean isDependent(Class<?> cls) {
		return false;
	}
	
}
