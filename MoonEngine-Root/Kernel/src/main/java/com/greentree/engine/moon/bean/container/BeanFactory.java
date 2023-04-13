package com.greentree.engine.moon.bean.container;

import java.util.stream.Stream;

import com.greentree.engine.moon.bean.definition.BeanDefinition;

public interface BeanFactory {
	
	BeanContainer newContainer(Stream<BeanDefinition> definitions);
	
}
