package com.greentree.engine.moon.bean.annotation;

import com.greentree.engine.moon.bean.ClassAnnotationBeanProcessor;

public class PackageScanEngineBeanProcessor implements ClassAnnotationBeanProcessor<BeanScan> {
	
	@Override
	public void processAnnotation(Object bean, BeanScan annotation) {
		System.out.println(bean);
	}
	
	
}
