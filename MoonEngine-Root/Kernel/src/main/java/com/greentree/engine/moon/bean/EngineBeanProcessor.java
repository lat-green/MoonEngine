package com.greentree.engine.moon.bean;

public interface EngineBeanProcessor {
	
	default Object processBeforeInitialization(Object bean) {
		return bean;
	}
	
	default Object processAfterInitialization(Object bean) {
		return bean;
	}
	
}
