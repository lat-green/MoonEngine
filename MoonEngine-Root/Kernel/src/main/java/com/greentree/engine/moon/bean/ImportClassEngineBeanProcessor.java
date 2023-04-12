package com.greentree.engine.moon.bean;

import java.lang.reflect.Method;

import com.greentree.engine.moon.bean.annotation.BeanScan;
import com.greentree.engine.moon.bean.annotation.EngineBean;
import com.greentree.engine.moon.bean.annotation.Import;

@BeanScan
@EngineBean
public class ImportClassEngineBeanProcessor
implements ClassAnnotationEngineBeanProcessor<Import>, MethodAnnotationEngineBeanProcessor<Import> {
	
	private EngineContext context;
	
	@Override
	public void process(Object bean) {
		ClassAnnotationEngineBeanProcessor.super.process(bean);
		MethodAnnotationEngineBeanProcessor.super.process(bean);
	}
	
	@Override
	public void process(Object bean, Method method, Import annotation) {
		for(var beanClass : annotation.value())
			context.addBean(beanClass);
	}
	
	@Override
	public void processAnnotation(Object bean, Import annotation) {
		for(var beanClass : annotation.value())
			context.addBean(beanClass);
	}
	
}
