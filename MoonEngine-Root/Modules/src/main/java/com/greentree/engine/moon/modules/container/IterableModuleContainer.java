package com.greentree.engine.moon.modules.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.greentree.engine.moon.modules.EngineModule;
import com.greentree.engine.moon.modules.EngineProperties;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.TerminateModule;
import com.greentree.engine.moon.modules.UpdateModule;


public class IterableModuleContainer implements ModuleContainer {
	
	private final Iterable<? extends LaunchModule> launchModules;
	private final Iterable<? extends TerminateModule> terminateModules;
	private final Iterable<? extends UpdateModule> updateModules;
	public IterableModuleContainer(Collection<? extends LaunchModule> launchModules,
			Collection<? extends TerminateModule> terminateModules, Collection<? extends UpdateModule> updateModules) {
		this.launchModules = Collections.unmodifiableCollection(launchModules);
		this.terminateModules = Collections.unmodifiableCollection(terminateModules);
		this.updateModules = Collections.unmodifiableCollection(updateModules);
	}
	
	public IterableModuleContainer(Collection<? extends EngineModule> modules) {
		var launchModules = new ArrayList<LaunchModule>();
		var terminateModules = new ArrayList<TerminateModule>();
		var updateModules = new ArrayList<UpdateModule>();
		for(var m : modules) {
			if(m instanceof LaunchModule lm)
				launchModules.add(lm);
			if(m instanceof UpdateModule um)
				updateModules.add(um);
			if(m instanceof TerminateModule tm)
				terminateModules.add(tm);
		}
		this.launchModules = launchModules;
		this.terminateModules = terminateModules;
		this.updateModules = updateModules;
	}
	
	@Override
	public void launch(EngineProperties properties) {
		for(var m : launchModules)
			m.launch(properties);
	}
	
	@Override
	public void terminate() {
		for(var m : terminateModules)
			m.terminate();
	}
	
	@Override
	public void update() {
		for(var m : updateModules)
			m.update();
	}
	
}
