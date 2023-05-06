package com.greentree.engine.moon.kernel.processor;

public interface EngineBeanProcessor {
	
	default void onEnable(Object bean) {
	}
	
	default void onDisable(Object bean) {
	}
	
	default Object process(Object bean) {
		return bean;
	}
	
}
