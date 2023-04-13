package com.greentree.engine.moon.bean.annotation;

import java.lang.reflect.Parameter;

import com.greentree.engine.moon.bean.ArgumentsInjector;
import com.greentree.engine.moon.bean.container.BeanContainer;

@EngineBean
public class ArgumentsInjectorImpl implements ArgumentsInjector {
	
	private BeanContainer context;
	
	@Override
	public Object argument(Parameter parameter) {
		return context.getBean(parameter.getType()).orElse(null);
	}
	
}
