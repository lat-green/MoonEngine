package com.greentree.engine.moon.bean.module;

import com.greentree.engine.moon.bean.ExtendsEngineBeanProcessor;
import com.greentree.engine.moon.bean.annotation.Autowired;
import com.greentree.engine.moon.bean.annotation.EngineBean;
import com.greentree.engine.moon.module.EngineProperties;
import com.greentree.engine.moon.module.LaunchModule;

@EngineBean
public class LaunchModuleProcessor implements ExtendsEngineBeanProcessor<LaunchModule> {
	
	@Autowired(required = true)
	private EngineProperties properties;
	
	@Override
	public void processExtends(LaunchModule bean) {
		bean.launch(properties);
	}
	
	
}
