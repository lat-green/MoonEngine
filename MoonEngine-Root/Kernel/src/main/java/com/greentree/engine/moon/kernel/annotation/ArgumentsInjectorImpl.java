package com.greentree.engine.moon.kernel.annotation;

import java.lang.reflect.Parameter;

import com.greentree.engine.moon.kernel.ArgumentsInjector;
import com.greentree.engine.moon.kernel.container.BeanContainer;

@EngineBean
public class ArgumentsInjectorImpl implements ArgumentsInjector {
	
	private BeanContainer context;
	
	@Override
	public Object argument(Parameter parameter) {
		return context.getBean(parameter.getType()).orElse(null);
	}
	
}
