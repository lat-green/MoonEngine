package com.greentree.engine.moon.kernel.annotation;

import java.lang.reflect.Method;

import com.greentree.engine.moon.kernel.ArgumentsInjector;
import com.greentree.engine.moon.kernel.MethodAnnotationBeanProcessor;

@EngineBean

public class PostConstructProcessor implements MethodAnnotationBeanProcessor<PostConstruct> {
	
	@Autowired(required = true)
	private ArgumentsInjector injector;
	
	@Override
	public void processAnnotation(Object bean, Method method, PostConstruct annotation) {
		injector.call(bean, method);
	}
	
}
