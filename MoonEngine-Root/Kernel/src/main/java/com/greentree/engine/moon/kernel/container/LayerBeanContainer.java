package com.greentree.engine.moon.kernel.container;

import java.util.stream.Stream;


public record LayerBeanContainer(BeanContainer parent, ConfigurableBeanContainer base)
implements ConfigurableBeanContainer {
	
	@Override
	public <T> Stream<T> getBeans(Class<T> cls) {
		return Stream.concat(parent.getBeans(cls), base.getBeans(cls));
	}
	
	@Override
	public void addBeans(Iterable<?> beans) {
		base.addBeans(beans);
	}
	
}
