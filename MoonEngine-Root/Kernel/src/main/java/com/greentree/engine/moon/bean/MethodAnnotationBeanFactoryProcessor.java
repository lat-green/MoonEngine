package com.greentree.engine.moon.bean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.stream.Stream;

import com.greentree.commons.util.classes.info.TypeUtil;
import com.greentree.engine.moon.bean.definition.BeanDefinition;
import com.greentree.engine.moon.bean.definition.BeanDefinitionProcessor;
import com.greentree.engine.moon.module.base.AnnotationUtil;

public interface MethodAnnotationBeanFactoryProcessor<A extends Annotation> extends BeanDefinitionProcessor {
	
	@SuppressWarnings("rawtypes")
	private Class<A> annotationType() {
		return TypeUtil.<A, MethodAnnotationBeanFactoryProcessor>getFirstAtgument(getClass(),
				MethodAnnotationBeanFactoryProcessor.class).toClass();
	}
	
	Stream<BeanDefinition> processAnnotation(BeanDefinition definition, Method method, A annotation);
	
	@Override
	default Stream<BeanDefinition> processDefinitionContainer(BeanDefinition definition) {
		return Stream.of(definition.type().getMethods())
				.flatMap(method ->{
					return AnnotationUtil.getAnnotations(method, annotationType())
							.flatMap(a -> processAnnotation(definition, method, a));
				});
	}
	
}
