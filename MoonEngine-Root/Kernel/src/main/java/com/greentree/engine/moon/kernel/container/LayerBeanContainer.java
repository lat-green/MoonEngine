package com.greentree.engine.moon.kernel.container;

import java.util.function.Consumer;
import java.util.stream.Stream;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.observable.ObjectObservable;


public record LayerBeanContainer(BeanContainer parent, ConfigurableBeanContainer base)
implements ConfigurableBeanContainer {
	
	@Override
	public <T> Stream<T> getBeans(Class<T> cls) {
		return Stream.concat(parent.getBeans(cls), base.getBeans(cls));
	}
	
	@Override
	public ObjectObservable<Object> action() {
		return new ObjectObservable<>() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public ListenerCloser addListener(Consumer<? super Object> listener) {
				return ListenerCloser.merge(parent.action().addListener(listener), base.action().addListener(listener));
			}
		};
	}
	
	@Override
	public Object addBean(Object bean) {
		return base.addBean(bean);
	}
	
}
