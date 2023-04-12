package com.greentree.engine.moon.bean;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

import com.greentree.engine.moon.bean.annotation.EngineBeanFactory;
import com.greentree.engine.moon.bean.annotation.EngineConfiguration;
import com.greentree.engine.moon.module.base.AnnotationUtil;

public class EngineConfigurationProcessor implements ClassAnnotationEngineBeanProcessor<EngineConfiguration> {
	
	private EngineContext context;
	
	@Override
	public void processAnnotation(Object bean, EngineConfiguration annotation) {
		Stream.of(bean.getClass().getMethods()).filter(x -> AnnotationUtil.hasAnnotation(x, EngineBeanFactory.class))
				.forEach(x ->
				{
					try {
						var args = new Object[x.getParameterCount()];
						for(int i = 0; i < args.length; i++)
							args[i] = context.getBean(x.getParameterTypes()[i]).orElse(null);
						context.addBean(x.invoke(bean, args));
					}catch(IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
				});
	}
	
}
