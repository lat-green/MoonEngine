package com.greentree.engine.moon.bean.annotation;

import com.greentree.engine.moon.bean.ClassAnnotationBeanProcessor;
import com.greentree.engine.moon.bean.ClassPathBeanScanner;
import com.greentree.engine.moon.bean.container.ConfigurableBeanContainer;

@EngineBean
public class PackageScanEngineBeanProcessor implements ClassAnnotationBeanProcessor<BeanScan> {
	
	private ClassPathBeanScanner scanner;
	private ConfigurableBeanContainer ctx;
	
	
	@Override
	public void processAnnotation(Object bean, BeanScan annotation) {
		scanner.scan(ctx, bean.getClass().getPackageName());
	}
	
	
}
