package com.greentree.engine.moon.bean.annotation;

import java.lang.reflect.Method;

import com.greentree.engine.moon.bean.ArgumentsInjector;
import com.greentree.engine.moon.bean.MethodAnnotationEngineBeanProcessor;


public class PostConstructProcessor implements MethodAnnotationEngineBeanProcessor<PostConstruct> {
	
	@Autowired(required = true)
	private ArgumentsInjector injector;
	
	@Override
	public void process(Object bean, Method method, PostConstruct annotation) {
		injector.call(bean, method);
	}
	
}
