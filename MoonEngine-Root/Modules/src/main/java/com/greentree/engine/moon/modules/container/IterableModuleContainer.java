package com.greentree.engine.moon.modules.container;

import java.util.Collection;
import java.util.Collections;

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
