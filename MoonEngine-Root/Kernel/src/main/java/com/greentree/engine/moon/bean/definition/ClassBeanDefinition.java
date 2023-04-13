package com.greentree.engine.moon.bean.definition;

import java.util.stream.Stream;

import com.greentree.commons.util.classes.ClassUtil;


public record ClassBeanDefinition(Class<?> type) implements BeanDefinition {
	
	public ClassBeanDefinition {
	}
	public ClassBeanDefinition(String name) throws ClassNotFoundException {
		this(Class.forName(name));
	}
	
	@Override
	public Stream<Class<?>> properties() {
		return ClassUtil.getAllFields(type).stream().map(x -> x.getType());
	}
	
}
