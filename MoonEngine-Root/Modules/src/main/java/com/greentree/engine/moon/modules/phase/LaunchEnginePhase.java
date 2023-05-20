package com.greentree.engine.moon.modules.phase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.greentree.engine.moon.kernel.annotation.EngineBean;
import com.greentree.engine.moon.modules.EngineProperties;
import com.greentree.engine.moon.modules.LaunchModule;

@EngineBean
public class LaunchEnginePhase implements EnginePhase<LaunchModule> {
	
	@Autowired
	private EngineProperties properties;
	
	@Autowired
	private ConfigurableListableBeanFactory beanFactory;
	
	@Override
	public void run(Iterable<? extends LaunchModule> modules) {
		for(var module : modules) {
			beanFactory.autowireBean(module);
			module.launch(properties);
		}
		
	}
	
	
}
