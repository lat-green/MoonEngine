package com.greentree.engine.moon.kernel;

import java.lang.annotation.Annotation;

import com.greentree.commons.util.classes.info.TypeUtil;
import com.greentree.engine.moon.kernel.annotation.AnnotationUtil;

public interface ClassAnnotationBeanProcessor<A extends Annotation> extends EngineBeanProcessor {
	
	@SuppressWarnings("rawtypes")
	private Class<A> annotationType() {
		return TypeUtil.<A, ClassAnnotationBeanProcessor>getFirstAtgument(getClass(),
				ClassAnnotationBeanProcessor.class).toClass();
	}
	
	@Override
	default void process(Object bean) {
		AnnotationUtil.getAnnotations(bean.getClass(), annotationType()).forEach(annotation -> {
			processAnnotation(bean, annotation);
		});
	}
	
	void processAnnotation(Object bean, A annotation);
	
}
