package com.greentree.engine.moon.kernel.annotation;

import java.lang.reflect.Method;

import com.greentree.engine.moon.kernel.ClassAnnotationBeanProcessor;
import com.greentree.engine.moon.kernel.MethodAnnotationBeanProcessor;
import com.greentree.engine.moon.kernel.container.ConfigurableBeanContainer;

@EngineBean
public class ImportClassBeanFactoryProcessor
		implements ClassAnnotationBeanProcessor<Import>, MethodAnnotationBeanProcessor<Import> {
	
	private ConfigurableBeanContainer ctx;
	
	@Override
	public void processAnnotation(Object bean, Method method, Import annotation) {
		processAnnotation(bean, annotation);
	}
	
	@Override
	public void processAnnotation(Object bean, Import annotation) {
		processAnnotation(annotation);
	}
	
	private void processAnnotation(Import annotation) {
		for(var imp : annotation.value())
			ctx.addBean(imp);
	}
	
	@Override
	public void process(Object bean) {
		ClassAnnotationBeanProcessor.super.process(bean);
		MethodAnnotationBeanProcessor.super.process(bean);
	}
	
}
