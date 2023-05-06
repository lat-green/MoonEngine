package com.greentree.engine.moon.kernel.processor;

import com.greentree.engine.moon.kernel.info.BeanInfo;
import com.greentree.engine.moon.kernel.info.NullBeanInfo;

public interface EngineBeanProcessor {
	
	default BeanInfo info(Object bean) {
		return new NullBeanInfo();
	}
	
	default void onEnable(Object bean) {
	}
	
	default void onDisable(Object bean) {
	}
	
	default Object process(Object bean) {
		return bean;
	}
	
}
