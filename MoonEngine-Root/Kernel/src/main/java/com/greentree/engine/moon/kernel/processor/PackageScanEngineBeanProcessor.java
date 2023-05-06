package com.greentree.engine.moon.kernel.processor;

import com.greentree.engine.moon.kernel.ClassPathBeanScanner;
import com.greentree.engine.moon.kernel.annotation.BeanScan;
import com.greentree.engine.moon.kernel.annotation.EngineBean;
import com.greentree.engine.moon.kernel.container.ConfigurableBeanContainer;

@EngineBean
public class PackageScanEngineBeanProcessor implements ClassAnnotationBeanProcessor<BeanScan> {
	
	private ClassPathBeanScanner scanner;
	private ConfigurableBeanContainer ctx;
	
	
	@Override
	public void processAnnotation(Object bean, BeanScan annotation) {
		scanner.scan(ctx, bean.getClass().getPackageName());
	}
	
	
}
