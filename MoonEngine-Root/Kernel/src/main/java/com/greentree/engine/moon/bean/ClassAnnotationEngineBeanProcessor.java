package com.greentree.engine.moon.bean;

import java.lang.annotation.Annotation;

import com.greentree.commons.util.classes.info.TypeUtil;
import com.greentree.engine.moon.module.base.AnnotationUtil;

public interface ClassAnnotationEngineBeanProcessor<A extends Annotation> extends EngineBeanProcessor {
	
	@Override
	default void process(Object bean) {
		AnnotationUtil.getAnnotations(bean.getClass(), annotationType()).forEach(annotation -> {
			processAnnotation(bean, annotation);
		});
	}
	
	void processAnnotation(Object bean, A annotation);
	
	@SuppressWarnings("unchecked")
	private Class<A> annotationType() {
		return (Class<A>) TypeUtil.getFirstAtgument(getClass(), ClassAnnotationEngineBeanProcessor.class).toClass();
	}
	
}
