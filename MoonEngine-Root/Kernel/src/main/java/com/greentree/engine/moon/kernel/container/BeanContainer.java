package com.greentree.engine.moon.kernel.container;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface BeanContainer {
	
	default boolean hasBean(Class<?> cls) {
		return getBean(cls).isPresent();
	}
	
	default <T> Optional<T> getBean(Class<T> cls) {
		return getBeans(cls).findAny();
	}
	
	default <T> T getBean(Class<T> cls, Supplier<? extends T> supplier) {
		return getBean(cls).orElseGet(supplier);
	}
	
	<T> Stream<T> getBeans(Class<T> cls);
	
	default boolean isBean(Object bean) {
		return (bean != null) && getBeans(bean.getClass()).anyMatch(x -> x.equals(bean));
	}
	
}
