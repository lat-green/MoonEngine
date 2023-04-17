package com.greentree.engine.moon.modules.container;

import java.util.List;

import com.greentree.engine.moon.modules.AnnotationUtil;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.TerminateModule;
import com.greentree.engine.moon.modules.UpdateModule;

public record AnnotatedModuleSorter() implements ModuleSorter {
	
	@Override
	public void sortLaunch(List<? extends LaunchModule> modules) {
		AnnotationUtil.sort(modules, "launch");
	}
	
	@Override
	public void sortTerminate(List<? extends TerminateModule> modules) {
		AnnotationUtil.sort(modules, "terminate");
	}
	
	@Override
	public void sortUpdate(List<? extends UpdateModule> modules) {
		AnnotationUtil.sort(modules, "update");
	}
	
}
