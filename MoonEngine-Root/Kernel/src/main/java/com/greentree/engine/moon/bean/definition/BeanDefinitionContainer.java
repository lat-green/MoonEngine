package com.greentree.engine.moon.bean.definition;

import java.util.Optional;

public interface BeanDefinitionContainer extends Iterable<BeanDefinition> {
	
	Optional<BeanDefinition> getBeanDefinition(Class<?> cls);
	void addBeanDefinition(BeanDefinition definition);
	
	default void addBeanDefinition(Class<?> cls) {
		addBeanDefinition(new ClassBeanDefinition(cls));
	}
	
}
