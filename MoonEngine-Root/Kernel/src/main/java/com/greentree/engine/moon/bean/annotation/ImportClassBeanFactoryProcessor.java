package com.greentree.engine.moon.bean.annotation;

import java.lang.reflect.Method;
import java.util.stream.Stream;

import com.greentree.engine.moon.bean.ClassAnnotationBeanFactoryProcessor;
import com.greentree.engine.moon.bean.MethodAnnotationBeanFactoryProcessor;
import com.greentree.engine.moon.bean.definition.BeanDefinition;
import com.greentree.engine.moon.bean.definition.ClassBeanDefinition;

public class ImportClassBeanFactoryProcessor
		implements ClassAnnotationBeanFactoryProcessor<Import>, MethodAnnotationBeanFactoryProcessor<Import> {
	
	@Override
	public Stream<BeanDefinition> processAnnotation(BeanDefinition definition, Method method,
			Import annotation) {
		return processAnnotation(definition, annotation);
	}
	
	@Override
	public Stream<BeanDefinition> processAnnotation(BeanDefinition definition, Import annotation) {
		return Stream.concat(Stream.of(definition), Stream.of(annotation.value()).map(x -> new ClassBeanDefinition(x)));
	}
	
	@Override
	public Stream<BeanDefinition> processDefinitionContainer(BeanDefinition beanFactory) {
		return Stream.concat(ClassAnnotationBeanFactoryProcessor.super.processDefinitionContainer(beanFactory),
				MethodAnnotationBeanFactoryProcessor.super.processDefinitionContainer(beanFactory));
	}
	
}
