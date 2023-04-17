package com.greentree.engine.moon.kernel.container;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.observable.ObjectObservable;
import com.greentree.commons.action.observer.object.EventAction;
import com.greentree.commons.util.classes.ClassUtil;


public class ArraySetBeanContainer implements ConfigurableBeanContainer {
	
	
	private final Collection<Object> beans = new CopyOnWriteArraySet<>();
	
	
	private final MyObjectObservable action = new MyObjectObservable();
	{
		addBean(this);
	}
	
	@Override
	public ObjectObservable<Object> action() {
		return action;
	}
	
	@Override
	public Object addBean(Object bean) {
		if(beans.add(bean))
			action.event(bean);
		return bean;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Stream<T> getBeans(Class<T> cls) {
		return beans.stream().filter(x -> ClassUtil.isExtends(cls, x.getClass())).map(x -> (T) x);
	}
	
	private final class MyObjectObservable extends EventAction<Object> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public ListenerCloser addListener(Consumer<? super Object> listener) {
			for(var bean : beans)
				listener.accept(bean);
			return super.addListener(listener);
		}
		
	}
	
	
}
