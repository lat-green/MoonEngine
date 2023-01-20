package com.greentree.engine.moon.module.base;

import java.util.ArrayList;
import java.util.Collection;

import com.greentree.engine.moon.module.EngineModule;
import com.greentree.engine.moon.module.ExitManager;
import com.greentree.engine.moon.module.ExitManagerProperty;
import com.greentree.engine.moon.module.LaunchModule;
import com.greentree.engine.moon.module.TerminateModule;
import com.greentree.engine.moon.module.UpdateModule;

public class EngineLoop implements ExitManager {
	
	private final Iterable<? extends LaunchModule> launch_modules;
	private final Iterable<? extends TerminateModule> terminate_modules;
	private final Iterable<? extends UpdateModule> update_modules;
	
	private boolean runnable = false;
	private EnginePropertiesBase properties;
	
	public EngineLoop(Collection<? extends EngineModule> iterable) {
		final var list = new ArrayList<EngineModule>();
		for(var m : iterable)
			list.add(m);
		final var launch_list = new ArrayList<>(list.stream().filter(m->m instanceof LaunchModule)
				.map(m->(LaunchModule) m).toList());
		AnnotationUtil.sort(launch_list, "launch");
		launch_modules = launch_list;
		final var terminate_list = new ArrayList<>(list.stream()
				.filter(m->m instanceof TerminateModule).map(m->(TerminateModule) m).toList());
		AnnotationUtil.sort(terminate_list, "terminate");
		terminate_modules = terminate_list;
		final var update_list = new ArrayList<>(list.stream().filter(m->m instanceof UpdateModule)
				.map(m->(UpdateModule) m).toList());
		AnnotationUtil.sort(update_list, "update");
		update_modules = update_list;
	}
	
	@Override
	public void exit() {
		runnable = false;
	}
	
	public void run() {
		if(runnable)
			throw new IllegalArgumentException("SceneManagerBase already run");
		runnable = true;
		
		properties = new EnginePropertiesBase();
		properties.add(new ExitManagerProperty(this));
		
		for(var m : launch_modules)
			m.launch(properties);
		
		while(runnable)
			for(var m : update_modules)
				m.update();
			
		for(var m : terminate_modules)
			m.terminate();
		properties.clear();
		properties = null;
	}
	
	
}
