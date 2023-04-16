package com.greentree.engine.moon.kernel.annotation;

import java.lang.reflect.Method;

import com.greentree.engine.moon.kernel.MethodAnnotationBeanProcessor;

public class EngineBeanFactoryProcessor implements MethodAnnotationBeanProcessor<EngineBeanFactory> {
	
	@Override
	public void processAnnotation(Object bean, Method method, EngineBeanFactory annotation) {
		System.out.println(method);
	}
	
	
	
}
