package com.greentree.engine.moon.bean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.greentree.commons.util.classes.info.TypeUtil;
import com.greentree.engine.moon.module.base.AnnotationUtil;

public interface MethodAnnotationEngineBeanProcessor<A extends Annotation> extends EngineBeanProcessor {
	
	@Override
	default void process(Object bean) {
		for(var method : bean.getClass().getMethods()) {
			var a = AnnotationUtil.getAnnotation(method, annotationType());
			if(a != null)
				process(bean, method, a);
		}
	}
	
	void process(Object bean, Method method, A annotation);
	
	@SuppressWarnings("unchecked")
	private Class<A> annotationType() {
		return (Class<A>) TypeUtil.getFirstAtgument(getClass(), MethodAnnotationEngineBeanProcessor.class).toClass();
	}
	
}
