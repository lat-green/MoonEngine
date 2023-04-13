package com.greentree.engine.moon.bean.container;

public interface BeanContainerBuilder extends BeanContainer {
	
	
	<T> T removeBean(Class<T> cls);
	
	<T> T addBean(T value);
	
	
	BeanContainer build();
	
}
