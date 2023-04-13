package com.greentree.engine.moon.bean.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import com.greentree.commons.util.classes.ClassUtil;


public final class BeanContainerBase implements BeanContainer {
	
	private final Collection<Object> beans;
	public BeanContainerBase(Collection<?> beans) {
		this.beans = new ArrayList<>(beans);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Stream<T> getBeans(Class<T> cls) {
		return beans.stream().filter(x -> ClassUtil.isExtends(cls, x.getClass())).map(x -> (T) x);
	}
	
}
