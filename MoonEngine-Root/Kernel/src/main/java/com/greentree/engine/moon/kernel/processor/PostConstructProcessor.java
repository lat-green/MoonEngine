package com.greentree.engine.moon.kernel.processor;

import java.lang.reflect.Method;

import com.greentree.engine.moon.kernel.ArgumentsInjector;
import com.greentree.engine.moon.kernel.annotation.Autowired;
import com.greentree.engine.moon.kernel.annotation.EngineBean;
import com.greentree.engine.moon.kernel.annotation.PostConstruct;

@EngineBean

public class PostConstructProcessor implements MethodAnnotationBeanProcessor<PostConstruct> {
	
	@Autowired(required = true)
	private ArgumentsInjector injector;
	
	@Override
	public Object processAnnotation(Object bean, Method method, PostConstruct annotation) {
		injector.call(bean, method);
		return bean;
	}
	
}
