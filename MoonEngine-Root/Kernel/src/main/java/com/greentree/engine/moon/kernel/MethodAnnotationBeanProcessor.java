package com.greentree.engine.moon.kernel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.greentree.commons.util.classes.info.TypeUtil;
import com.greentree.engine.moon.kernel.annotation.AnnotationUtil;

public interface MethodAnnotationBeanProcessor<A extends Annotation> extends EngineBeanProcessor {
	
	@Override
	default Object process(Object bean) {
		for(var method : bean.getClass().getMethods()) {
			var a = AnnotationUtil.getAnnotation(method, annotationType());
			if(a != null)
				processAnnotation(bean, method, a);
		}
		return bean;
	}
	
	void processAnnotation(Object bean, Method method, A annotation);
	
	@SuppressWarnings("rawtypes")
	private Class<A> annotationType() {
		return TypeUtil.<A, MethodAnnotationBeanProcessor>getFirstAtgument(getClass(),
				MethodAnnotationBeanProcessor.class).toClass();
	}
	
}
