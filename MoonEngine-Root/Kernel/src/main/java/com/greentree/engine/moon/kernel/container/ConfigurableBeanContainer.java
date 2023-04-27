package com.greentree.engine.moon.kernel.container;

import com.greentree.commons.util.iterator.IteratorUtil;

public interface ConfigurableBeanContainer extends BeanContainer {
	
	default <T> T addBean(T bean) {
		addBeans(bean);
		return bean;
	}
	
	default void addBeans(Object... beans) {
		addBeans(IteratorUtil.iterable(beans));
	}
	
	void addBeans(Iterable<?> beans);
	
}
