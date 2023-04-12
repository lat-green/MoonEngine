package com.greentree.engine.moon.bean;

import java.lang.reflect.Method;

import com.greentree.engine.moon.bean.annotation.EngineBeanFactory;

public class EngineBeanFactoryProcessor implements MethodAnnotationEngineBeanProcessor<EngineBeanFactory> {
	
	@Override
	public void process(Object bean, Method method, EngineBeanFactory annotation) {
		System.out.println(method);
	}
	
	
	
}
