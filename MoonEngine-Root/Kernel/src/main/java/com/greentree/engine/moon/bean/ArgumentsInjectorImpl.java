package com.greentree.engine.moon.bean;

import java.lang.reflect.Parameter;

import com.greentree.engine.moon.bean.annotation.EngineBean;

@EngineBean
public class ArgumentsInjectorImpl implements ArgumentsInjector {
	
	private EngineContext context;
	
	@Override
	public Object argument(Parameter parameter) {
		return context.getBean(parameter.getType()).orElse(null);
	}
	
}
