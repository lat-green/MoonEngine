package com.greentree.engine.moon.bean.module;

import java.util.ServiceLoader;

import com.greentree.engine.moon.bean.annotation.EngineBean;
import com.greentree.engine.moon.bean.annotation.PostConstruct;
import com.greentree.engine.moon.bean.container.ConfigurableBeanContainer;
import com.greentree.engine.moon.module.EngineModule;

@EngineBean
public class ModuleConfiguration {
	
	private ConfigurableBeanContainer context;
	
	@PostConstruct
	public void findModules() {
		for(var m : ServiceLoader.load(EngineModule.class))
			context.addBean(m);
	}
	
}
