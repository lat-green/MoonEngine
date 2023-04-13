package com.greentree.engine.moon.bean.annotation;

import java.lang.reflect.Method;

import com.greentree.engine.moon.bean.ArgumentsInjector;
import com.greentree.engine.moon.bean.MethodAnnotationBeanProcessor;

@EngineBean

public class PostConstructProcessor implements MethodAnnotationBeanProcessor<PostConstruct> {
	
	@Autowired(required = true)
	private ArgumentsInjector injector;
	
	@Override
	public void processAnnotation(Object bean, Method method, PostConstruct annotation) {
		injector.call(bean, method);
	}
	
}
