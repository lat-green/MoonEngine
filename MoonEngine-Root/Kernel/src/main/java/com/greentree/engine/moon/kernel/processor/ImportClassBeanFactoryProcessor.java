package com.greentree.engine.moon.kernel.processor;

import java.lang.reflect.Method;

import com.greentree.engine.moon.kernel.annotation.EngineBean;
import com.greentree.engine.moon.kernel.annotation.Import;
import com.greentree.engine.moon.kernel.container.ConfigurableBeanContainer;

@EngineBean
public class ImportClassBeanFactoryProcessor
		implements ClassAnnotationBeanProcessor<Import>, MethodAnnotationBeanProcessor<Import> {
	
	private ConfigurableBeanContainer ctx;
	
	@Override
	public Object processAnnotation(Object bean, Method method, Import annotation) {
		processAnnotation(bean, annotation);
		return bean;
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
		bean = MethodAnnotationBeanProcessor.super.process(bean);
		return bean;
	}
	
}
