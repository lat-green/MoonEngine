package com.greentree.engine.moon.bean;

import com.greentree.commons.util.classes.ClassUtil;
import com.greentree.commons.util.classes.info.TypeUtil;

public interface ExtendsEngineBeanProcessor<T> extends EngineBeanProcessor {
	
	@SuppressWarnings("unchecked")
	@Override
	default void process(Object bean) {
		if(ClassUtil.isExtends(superType(), bean.getClass())) {
			processExtends((T) bean);
		}
	}
	
	void processExtends(T bean);
	
	@SuppressWarnings("unchecked")
	private Class<T> superType() {
		return (Class<T>) TypeUtil.getFirstAtgument(getClass(), ExtendsEngineBeanProcessor.class).toClass();
	}
	
}
