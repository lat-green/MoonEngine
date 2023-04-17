package com.greentree.engine.moon.kernel.container;

import java.util.Optional;
import java.util.stream.Stream;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.observable.ObjectObservable;
import com.greentree.engine.moon.kernel.EngineBeanProcessor;

public interface BeanContainer {
	
	default ListenerCloser enableBeanProcessors() {
		return action().addListener(bean -> {
			var processors = getBeans(EngineBeanProcessor.class).toList();
			for(var p : processors) {
				var obj = p.process(bean);
				if(obj != bean)
					throw new UnsupportedOperationException(bean + " " + obj);
			}
		});
	}
	default boolean hasBean(Class<?> cls) {
		return getBean(cls).isPresent();
	}
	
	default <T> Optional<T> getBean(Class<T> cls) {
		return getBeans(cls).findAny();
	}
	
	<T> Stream<T> getBeans(Class<T> cls);
	
	default boolean isBean(Object bean) {
		return (bean != null) && getBeans(bean.getClass()).anyMatch(x -> x.equals(bean));
	}
	
	ObjectObservable<Object> action();
	
}
