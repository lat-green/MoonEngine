package com.greentree.engine.moon.bean;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;

import com.greentree.commons.util.classes.info.TypeUtil;
import com.greentree.engine.moon.bean.definition.BeanDefinition;
import com.greentree.engine.moon.bean.definition.BeanDefinitionProcessor;
import com.greentree.engine.moon.module.base.AnnotationUtil;

public interface ClassAnnotationBeanFactoryProcessor<A extends Annotation> extends BeanDefinitionProcessor {
	
	@SuppressWarnings("rawtypes")
	private Class<A> annotationType() {
		return TypeUtil.<A, ClassAnnotationBeanFactoryProcessor>getFirstAtgument(getClass(),
				ClassAnnotationBeanFactoryProcessor.class).toClass();
	}
	
	@Override
	default Stream<BeanDefinition> processDefinitionContainer(BeanDefinition definition) {
		return AnnotationUtil.getAnnotations(definition.type(), annotationType())
				.flatMap(x -> processAnnotation(definition, x));
	}
	
	Stream<BeanDefinition> processAnnotation(BeanDefinition definition,
			A annotation);
	
}
