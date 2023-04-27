package com.greentree.engine.moon.kernel.container;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Stream;

import com.greentree.commons.util.classes.ClassUtil;


public class ArraySetBeanContainer implements ConfigurableBeanContainer {
	
	
	private final Collection<Object> beans = new CopyOnWriteArraySet<>();
	
	{
		addBean(this);
	}
	
	@Override
	public void addBeans(Iterable<?> beans) {
		for(var bean : beans)
			this.beans.add(bean);
	}
	
	@Override
	public void addBeans(Object... beans) {
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Stream<T> getBeans(Class<T> cls) {
		return beans.stream().filter(x -> ClassUtil.isExtends(cls, x.getClass())).map(x -> (T) x);
	}
	
}
