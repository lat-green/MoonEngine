package com.greentree.engine.moon.kernel.container;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Stream;

import com.greentree.commons.util.classes.ClassUtil;
import com.greentree.engine.moon.kernel.processor.EngineBeanProcessor;


public class ArraySetBeanContainer implements ConfigurableBeanContainer {
	
	
	private final Set<Object> beans = new CopyOnWriteArraySet<>();
	
	{
		addBean(this);
	}
	
	@Override
	public <T> T addBean(T bean) {
		if(beans.contains(bean))
			return bean;
		var processors = processors();
		beans.add(bean);
		processors.forEach(x -> x.process(bean));
		if(bean instanceof EngineBeanProcessor p)
			for(var b : this.beans)
				p.process(b);
		return bean;
	}
	
	@Override
	public void addBeans(Iterable<?> beans) {
		for(var bean : beans)
			addBean(bean);
	}
	
	private Stream<EngineBeanProcessor> processors() {
		return getBeans(EngineBeanProcessor.class);
	}
	
	@Override
	public <T> Stream<T> getBeans(Class<T> cls) {
		return beans.stream().filter(x -> ClassUtil.isExtends(cls, x.getClass())).map(x -> (T) x);
	}
	
}
