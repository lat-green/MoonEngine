package com.greentree.engine.moon.kernel.annotation;

import java.util.Optional;

import com.greentree.commons.injector.DependencyScanner;
import com.greentree.commons.injector.InjectionContainer;
import com.greentree.commons.injector.Injector;
import com.greentree.engine.moon.kernel.EngineBeanProcessor;
import com.greentree.engine.moon.kernel.container.BeanContainer;


public class InjectBeanProcessor implements EngineBeanProcessor {
	
	
	private final BeanContainer container;
	
	public InjectBeanProcessor(BeanContainer container) {
		this.container = container;
	}
	
	@Override
	public void process(Object bean) {
		var sc = container.getBean(DependencyScanner.class)
				.orElseGet(() -> new DependencyScannerConfig().newDependencyScanner());
		var inject = new Injector(new InjectionContainer() {
			
			@Override
			public <T> Optional<T> get(Class<T> cls) {
				return container.getBean(cls);
			}
			
		}, sc);
		inject.inject(bean);
	}
	
}
