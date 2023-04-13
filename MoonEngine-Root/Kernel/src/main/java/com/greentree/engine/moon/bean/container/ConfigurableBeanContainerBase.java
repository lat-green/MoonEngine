package com.greentree.engine.moon.bean.container;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import com.greentree.commons.injector.InjectionContainer;
import com.greentree.commons.injector.Injector;
import com.greentree.commons.util.classes.ClassUtil;
import com.greentree.engine.moon.bean.EngineBeanProcessor;


public class ConfigurableBeanContainerBase implements ConfigurableBeanContainer, InjectionContainer {
	
	private final Collection<Object> beans = new CopyOnWriteArrayList<>();
	
	{
		beans.add(this);
	}
	
	@Override
	public Object addBean(Object bean) {
		if(beans.contains(bean))
			return bean;
		beans.add(bean);
		inject(bean);
		for(var p : processor().toList())
			bean = p.process(bean);
		if(bean instanceof EngineBeanProcessor p) {
			var c = beans.stream().map(x -> p.process(x)).toList();
			beans.clear();
			beans.addAll(c);
			bean = p.process(bean);
		}
		return bean;
	}
	
	@Override
	public <T> Optional<T> get(Class<T> cls) {
		return getBean(cls);
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
	
	private void inject(Object bean) {
		var injector = new Injector(this, new AutowiredFieldDependencyScanner());
		injector.inject(bean);
	}
	
	private Stream<EngineBeanProcessor> processor() {
		return getBeans(EngineBeanProcessor.class);
	}
	
}
