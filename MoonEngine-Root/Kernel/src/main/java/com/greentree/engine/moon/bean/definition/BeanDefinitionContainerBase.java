package com.greentree.engine.moon.bean.definition;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArraySet;

public class BeanDefinitionContainerBase implements BeanDefinitionContainer {
	
	private final Collection<BeanDefinition> raw = new CopyOnWriteArraySet<>();
	@Override
	public Iterator<BeanDefinition> iterator() {
		return raw.iterator();
	}
	
	@Override
	public Optional<BeanDefinition> getBeanDefinition(Class<?> cls) {
		return raw.stream().filter(x -> cls.isInstance(x)).findAny();
	}
	
	@Override
	public void addBeanDefinition(BeanDefinition definition) {
		raw.add(definition);
	}
	
	@Override
	public String toString() {
		return "BeanDefinitionContainerBase [" + raw + "]";
	}
	
}
