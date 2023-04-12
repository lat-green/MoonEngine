package com.greentree.engine.moon.bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public interface ArgumentsInjector {
	
	default Object[] arguments(Method method) {
		var args = new Object[method.getParameterCount()];
		for(int i = 0; i < args.length; i++)
			args[i] = argument(method.getParameters()[i]);
		return args;
	}
	
	Object argument(Parameter parameter);
	
	default void call(Object obj, Method method) {
		try {
			method.invoke(obj, arguments(method));
		}catch(IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
}
