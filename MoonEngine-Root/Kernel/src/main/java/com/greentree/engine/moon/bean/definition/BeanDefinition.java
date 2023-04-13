package com.greentree.engine.moon.bean.definition;

import java.util.stream.Stream;

public interface BeanDefinition {
	
	Class<?> type();
	Stream<Class<?>> properties();
	
}
