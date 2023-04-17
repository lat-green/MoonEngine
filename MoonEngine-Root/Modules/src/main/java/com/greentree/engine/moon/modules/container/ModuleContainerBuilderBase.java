package com.greentree.engine.moon.modules.container;

import java.util.ArrayList;
import java.util.Collection;

import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.TerminateModule;
import com.greentree.engine.moon.modules.UpdateModule;

public class ModuleContainerBuilderBase implements ModuleContainerBuilder {
	
	private final Collection<ModuleDefenition> modules = new ArrayList<>();
	
	private final ModuleSorter sorter;
	
	public ModuleContainerBuilderBase(ModuleSorter sorter) {
		this.sorter = sorter;
	}
	
	@Override
	public ModuleContainerBuilder addModule(ModuleDefenition module) {
		modules.add(module);
		return this;
	}
	
	@Override
	public ModuleContainer build() {
		var list = modules.stream().map(ModuleDefenition::build).toList();
		var launch_list = new ArrayList<>(
				list.stream().filter(m -> m instanceof LaunchModule).map(m -> (LaunchModule) m).toList());
		sorter.sortLaunch(launch_list);
		var terminate_list = new ArrayList<>(
				list.stream().filter(m -> m instanceof TerminateModule).map(m -> (TerminateModule) m).toList());
		var update_list = new ArrayList<>(
				list.stream().filter(m -> m instanceof UpdateModule).map(m -> (UpdateModule) m).toList());
		sorter.sortLaunch(launch_list);
		sorter.sortTerminate(terminate_list);
		sorter.sortUpdate(update_list);
		return new IterableModuleContainer(launch_list, terminate_list, update_list);
	}
	
}
