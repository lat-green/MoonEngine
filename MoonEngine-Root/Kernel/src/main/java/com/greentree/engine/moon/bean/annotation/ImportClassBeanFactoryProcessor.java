package com.greentree.engine.moon.bean.annotation;

import java.lang.reflect.Method;

import com.greentree.engine.moon.bean.ClassAnnotationBeanProcessor;
import com.greentree.engine.moon.bean.MethodAnnotationBeanProcessor;
import com.greentree.engine.moon.bean.container.ConfigurableBeanContainer;

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
	public Object process(Object bean) {
		bean = ClassAnnotationBeanProcessor.super.process(bean);
		bean = ClassAnnotationBeanProcessor.super.process(bean);
		return bean;
	}
	
}
