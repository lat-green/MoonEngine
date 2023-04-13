package com.greentree.engine.moon.bean.definition;

import java.util.stream.Stream;

public interface BeanDefinitionProcessor {
	
	Stream<BeanDefinition> processDefinitionContainer(BeanDefinition definition);
	
}
