package com.greentree.engine.moon.kernel.container;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public interface BeanContainer {
	
	default <T> Optional<T> getBean(Class<T> cls) {
		Objects.requireNonNull(cls);
		return getBeans(cls).findAny();
	}
	
	<T> Stream<T> getBeans(Class<T> cls);
	
	default Stream<Object> getBeans() {
		return getBeans(Object.class);
	}
	default boolean hasBean(Class<?> cls) {
		Objects.requireNonNull(cls);
		return getBean(cls).isPresent();
	}
	
	default boolean isBean(Object bean) {
		Objects.requireNonNull(bean);
		return getBeans(bean.getClass()).anyMatch(x -> x.equals(bean));
	}
	
}
