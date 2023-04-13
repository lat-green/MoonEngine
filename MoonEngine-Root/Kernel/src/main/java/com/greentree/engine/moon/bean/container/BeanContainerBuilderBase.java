package com.greentree.engine.moon.bean.container;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import com.greentree.commons.util.classes.ClassUtil;


public class BeanContainerBuilderBase implements BeanContainerBuilder {
	
	private final Collection<Object> beans = new CopyOnWriteArrayList<>();
	
	@Override
	public <T> T addBean(T bean) {
		if(beans.contains(bean))
			return bean;
		beans.add(bean);
		return bean;
	}
	@Override
	public BeanContainer build() {
		return new BeanContainerBase(beans);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Stream<T> getBeans(Class<T> cls) {
		return beans.stream().filter(x -> ClassUtil.isExtends(cls, x.getClass())).map(x -> (T) x);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T removeBean(Class<T> cls) {
		var iter = beans.iterator();
		while(iter.hasNext()) {
			var v = iter.next();
			if(cls.isInstance(v)) {
				iter.remove();
				return (T) v;
			}
		}
		return null;
	}
	
}
