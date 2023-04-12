package com.greentree.engine.moon.module.base;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import com.greentree.commons.injector.Constructor;
import com.greentree.commons.injector.DependencyScanner;
import com.greentree.commons.injector.InjectionContainer;
import com.greentree.commons.injector.Injector;
import com.greentree.commons.injector.MergeDependencyScanner;
import com.greentree.commons.util.classes.ClassUtil;
import com.greentree.engine.moon.bean.EngineBeanProcessor;
import com.greentree.engine.moon.bean.EngineContext;
import com.greentree.engine.moon.bean.annotation.EngineBean;


public class EngineContextBase implements EngineContext, InjectionContainer {
	
	private static final DependencyScanner SCANNER = new MergeDependencyScanner(new AutowiredFieldDependencyScanner());
	private final Collection<Object> beans = new CopyOnWriteArrayList<>();
	
	{
		beans.add(this);
	}
	
	@Override
	public <T> T addBean(Class<T> cls) {
		var beanOpt = getBean(cls);
		if(beanOpt.isPresent())
			return beanOpt.get();
		return addBean(newBean(cls));
	}
	
	@Override
	public <T> T addBean(T bean) {
		beans.add(bean);
		for(var p : processors())
			p.process(bean);
		if(bean instanceof EngineBeanProcessor p)
			for(var b : beans)
				p.process(b);
		return bean;
	}
	
	private <T> T addBean(Constructor<T> beanConstructor) {
		var bean = beanConstructor.value();
		beans.add(bean);
		beanConstructor.inject();
		for(var p : processors())
			p.process(bean);
		if(bean instanceof EngineBeanProcessor p)
			for(var b : beans)
				p.process(b);
		return bean;
	}
	
	@Override
	public <T> Optional<T> get(Class<T> cls) {
		return getBean(cls).or(() -> tryAddBean(cls));
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Stream<T> getBeans(Class<T> cls) {
		return beans.stream().filter(x -> ClassUtil.isExtends(cls, x.getClass())).map(x -> (T) x);
	}
	
	@Override
	public boolean isBean(Object bean) {
		return beans.contains(bean);
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
	
	private <T> Constructor<T> newBean(Class<T> cls) {
		var injector = new Injector(this, SCANNER);
		try {
			return injector.newInstace(cls);
		}catch(NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Iterable<? extends EngineBeanProcessor> processors() {
		return beans.stream().filter(x -> x instanceof EngineBeanProcessor).map(x -> (EngineBeanProcessor) x)
				.toList();
	}
	
	private <T> Optional<T> tryAddBean(Class<T> cls) {
		var beanOpt = getBean(cls);
		if(beanOpt.isPresent())
			return beanOpt;
		if(AnnotationUtil.hasAnnotation(cls, EngineBean.class)) {
			var bean = tryNewBean(cls);
			if(bean.isPresent()) {
				var c = bean.get();
				return Optional.of(addBean(c));
			}
		}
		return Optional.empty();
	}
	
	private <T> Optional<Constructor<T>> tryNewBean(Class<T> cls) {
		var injector = new Injector(this, SCANNER);
		Constructor<T> value;
		try {
			value = injector.newInstace(cls);
		}catch(NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			value = null;
		}
		return Optional.ofNullable(value);
	}
	
}
