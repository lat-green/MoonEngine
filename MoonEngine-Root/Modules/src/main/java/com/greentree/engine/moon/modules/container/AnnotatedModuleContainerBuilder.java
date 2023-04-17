package com.greentree.engine.moon.modules.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.TerminateModule;
import com.greentree.engine.moon.modules.UpdateModule;
import com.greentree.engine.moon.modules.base.AnnotationUtil;
import com.greentree.engine.moon.modules.base.WrapModule;

public class AnnotatedModuleContainerBuilder implements ModuleContainerBuilder {
	
	private final Collection<EngineModuleDefenition> modules = new ArrayList<>();
	
	@Override
	public ModuleContainerBuilder addModule(EngineModuleDefenition module) {
		modules.add(module);
		return this;
	}
	
	@Override
	public ModuleContainer build() {
		var list = modules.stream().map(EngineModuleDefenition::build).toList();
		List<? extends LaunchModule> launch_list = new ArrayList<>(
				list.stream().filter(m -> m instanceof LaunchModule).map(m -> (LaunchModule) m).toList());
		AnnotationUtil.sort(launch_list, "launch");
		launch_list = launch_list.stream().map(WrapModule::new).toList();
		List<? extends TerminateModule> terminate_list = new ArrayList<>(
				list.stream().filter(m -> m instanceof TerminateModule).map(m -> (TerminateModule) m).toList());
		AnnotationUtil.sort(terminate_list, "terminate");
		terminate_list = terminate_list.stream().map(WrapModule::new).toList();
		List<? extends UpdateModule> update_list = new ArrayList<>(
				list.stream().filter(m -> m instanceof UpdateModule).map(m -> (UpdateModule) m).toList());
		AnnotationUtil.sort(update_list, "update");
		update_list = update_list.stream().map(WrapModule::new).toList();
		return new IterableModuleContainer(launch_list, terminate_list, update_list);
	}
	
}
