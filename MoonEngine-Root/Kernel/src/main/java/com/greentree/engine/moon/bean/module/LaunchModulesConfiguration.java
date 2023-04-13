package com.greentree.engine.moon.bean.module;

import java.util.ArrayList;
import java.util.stream.Stream;

import com.greentree.engine.moon.bean.annotation.EngineBean;
import com.greentree.engine.moon.bean.container.BeanContainer;
import com.greentree.engine.moon.module.LaunchModule;
import com.greentree.engine.moon.module.base.AnnotationUtil;

@EngineBean
public class LaunchModulesConfiguration implements LaunchModules {
	
	private BeanContainer container;
	
	@Override
	public Stream<LaunchModule> launchModules() {
		var list = new ArrayList<>(container.getBeans(LaunchModule.class).toList());
		AnnotationUtil.sortInit(list);
		return list.stream();
	}
	
}
