package com.greentree.engine.moon.kernel.processor;

import com.greentree.commons.util.classes.ClassUtil;
import com.greentree.commons.util.classes.info.TypeUtil;

public interface ExtendsEngineBeanProcessor<T> extends EngineBeanProcessor {
	
	@SuppressWarnings("unchecked")
	@Override
	default Object process(Object bean) {
		if(ClassUtil.isExtends(superType(), bean.getClass())) {
			return processExtends((T) bean);
		}
		return bean;
	}
	
	Object processExtends(T bean);
	
	@SuppressWarnings("unchecked")
	private Class<T> superType() {
		return (Class<T>) TypeUtil.getFirstAtgument(getClass(), ExtendsEngineBeanProcessor.class).toClass();
	}
	
}
