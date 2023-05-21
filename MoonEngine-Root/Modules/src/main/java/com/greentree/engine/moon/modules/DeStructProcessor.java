package com.greentree.engine.moon.modules;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.greentree.commons.util.classes.ClassUtil;
import com.greentree.engine.moon.kernel.annotation.EngineBean;

@EngineBean
public class DeStructProcessor implements BeanPostProcessor {
	
	private final ConfigurableListableBeanFactory beanFactory;
	public DeStructProcessor(ConfigurableListableBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if(bean instanceof EngineProperty property) {
			var cls = property.getClass();
			for(var field : cls.getDeclaredFields()) {
				final Object value;
				try {
					value = ClassUtil.getField(property, field);
				}catch(IllegalArgumentException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
				addBean(value);
			}
		}
		return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
	}
	
	private void addBean(Object bean) {
		var beanName = bean.getClass().getSimpleName();
		beanFactory.registerSingleton(beanName, bean);
		beanFactory.autowireBean(bean);
		beanFactory.initializeBean(bean, beanName);
	}
	
}
