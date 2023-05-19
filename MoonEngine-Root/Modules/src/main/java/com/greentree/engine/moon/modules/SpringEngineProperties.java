package com.greentree.engine.moon.modules;

import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;

import com.greentree.engine.moon.kernel.annotation.EngineBean;

@EngineBean
public final class SpringEngineProperties implements EngineProperties {
	
	@Autowired
	private ConfigurableListableBeanFactory factory;
	@Autowired
	private ConfigurableApplicationContext context;
	
	@Override
	public Iterator<EngineProperty> iterator() {
		return context.getBeansOfType(EngineProperty.class).values().iterator();
	}
	
	@Override
	public void add(EngineProperty property) {
		var beanName = property.getClass().getSimpleName();
		factory.registerSingleton(beanName, property);
		factory.autowireBean(property);
		factory.initializeBean(property, beanName);
	}
	
	@Override
	public <T extends EngineProperty> Optional<T> getProperty(Class<T> cls) {
		if(context.getBeansOfType(cls).isEmpty()) {
			if(cls.isRecord()) {
				var registry = (BeanDefinitionRegistry) factory;
				var beanName = cls.getSimpleName();
				registry.registerBeanDefinition(beanName, new RootBeanDefinition(cls));
				var property = factory.getBean(beanName, cls);
				//				factory.initializeBean(property, beanName);
				return Optional.of(property);
			}
			return Optional.empty();
		}
		return Optional.of(context.getBean(cls));
	}
	
	
	
}
