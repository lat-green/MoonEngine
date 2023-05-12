package com.greentree.engine.moon.modules.phase;

import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.greentree.engine.moon.kernel.annotation.EngineBean;
import com.greentree.engine.moon.modules.EngineModule;
import com.greentree.engine.moon.modules.EngineProperties;
import com.greentree.engine.moon.modules.LaunchModule;

@EngineBean
public class LaunchEnginePhase implements EnginePhase {
	
	@Autowired
	private EngineProperties properties;
	
	@Autowired
	private MethodModuleSorter sorter;
	
	@Autowired
	private ConfigurableListableBeanFactory beanFactory;
	
	@Override
	public void run(Iterable<? extends EngineModule> modules) {
		var list = StreamSupport.stream(modules.spliterator(), false).filter(x -> x instanceof LaunchModule)
				.map(x -> (LaunchModule) x).collect(ArrayListCollector.INSTANCE());
		sorter.sort(list, "launch");
		for(var module : list) {
			beanFactory.autowireBean(module);
			module.launch(properties);
		}
		
	}
	
	
}
