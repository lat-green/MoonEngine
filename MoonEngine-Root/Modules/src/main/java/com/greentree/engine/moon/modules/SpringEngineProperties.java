package com.greentree.engine.moon.modules;

import java.util.Iterator;
import java.util.Optional;

import org.springframework.context.ConfigurableApplicationContext;

import com.greentree.engine.moon.kernel.annotation.EngineBean;

@EngineBean
public final class SpringEngineProperties implements EngineProperties {
	
	private final ConfigurableApplicationContext context;
	
	public SpringEngineProperties(ConfigurableApplicationContext context) {
		this.context = context;
	}
	
	@Override
	public Iterator<EngineProperty> iterator() {
		return context.getBeansOfType(EngineProperty.class).values().iterator();
	}
	
	@Override
	public void add(EngineProperty property) {
		var factory = context.getBeanFactory();
		var beanName = property.getClass().getSimpleName();
		factory.registerSingleton(beanName, property);
		factory.autowireBean(property);
		factory.initializeBean(property, beanName);
		
		//		factory.configureBean(property, beanName);
		//		System.out.println("add " + property);
	}
	
	@Override
	public <T extends EngineProperty> Optional<T> getProperty(Class<T> cls) {
		if(context.getBeansOfType(cls).isEmpty())
			return Optional.empty();
		return Optional.of(context.getBean(cls));
	}
	
	
	
}
