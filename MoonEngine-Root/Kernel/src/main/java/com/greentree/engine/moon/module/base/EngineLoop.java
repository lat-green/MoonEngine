package com.greentree.engine.moon.module.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.greentree.engine.moon.module.EngineModule;
import com.greentree.engine.moon.module.EngineProperties;
import com.greentree.engine.moon.module.ExitManager;
import com.greentree.engine.moon.module.ExitManagerProperty;
import com.greentree.engine.moon.module.LaunchModule;
import com.greentree.engine.moon.module.TerminateModule;
import com.greentree.engine.moon.module.UpdateModule;

public class EngineLoop implements ExitManager {
	
	private final List<? extends LaunchModule> launch_modules;
	private final List<? extends TerminateModule> terminate_modules;
	private final List<? extends UpdateModule> update_modules;
	
	private boolean runnable = false;
	private final EngineProperties properties;
	
	public EngineLoop(Collection<? extends EngineModule> iterable) {
		this(iterable, new EnginePropertiesBase());
	}
	
	public EngineLoop(Collection<? extends EngineModule> iterable, EngineProperties properties) {
		this.properties = properties;
		properties.add(new ExitManagerProperty(this));
		final var list = new ArrayList<EngineModule>();
		for(var m : iterable)
			list.add(m);
		List<? extends LaunchModule> launch_list = new ArrayList<>(
				list.stream().filter(m -> m instanceof LaunchModule).map(m -> (LaunchModule) m).toList());
		AnnotationUtil.sort(launch_list, "launch");
		launch_list = launch_list.stream().map(x -> new WrapModule(x)).toList();
		launch_modules = launch_list;
		List<? extends TerminateModule> terminate_list = new ArrayList<>(
				list.stream().filter(m -> m instanceof TerminateModule)
				.map(m -> (TerminateModule) m)
						.toList());
		AnnotationUtil.sort(terminate_list, "terminate");
		terminate_list = terminate_list.stream().map(x -> new WrapModule(x)).toList();
		terminate_modules = terminate_list;
		List<? extends UpdateModule> update_list =
				new ArrayList<>(
						list.stream().filter(m -> m instanceof UpdateModule).map(m -> (UpdateModule) m).toList());
		AnnotationUtil.sort(update_list, "update");
		update_list = update_list.stream().map(x -> new WrapModule(x)).toList();
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
		
		
		System.out.println("ls");
		for(var m : launch_modules)
			m.launch(properties);
		System.out.println("le");
		
		while(runnable)
			for(var m : update_modules)
				m.update();
			
		for(var m : terminate_modules)
			m.terminate();
	}
	
	
}
