package com.greentree.engine.moon.kernel.processor;

import java.lang.reflect.Method;

import com.greentree.engine.moon.kernel.annotation.EngineBeanFactory;

public class EngineBeanFactoryProcessor implements MethodAnnotationBeanProcessor<EngineBeanFactory> {
	
	@Override
	public Object processAnnotation(Object bean, Method method, EngineBeanFactory annotation) {
		System.out.println(method);
		return bean;
	}
	
	
	
}
