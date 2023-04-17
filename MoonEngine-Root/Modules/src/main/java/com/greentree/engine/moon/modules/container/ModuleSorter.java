package com.greentree.engine.moon.modules.container;

import java.util.List;

import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.TerminateModule;
import com.greentree.engine.moon.modules.UpdateModule;

public interface ModuleSorter {
	
	void sortLaunch(List<? extends LaunchModule> modules);
	void sortTerminate(List<? extends TerminateModule> modules);
	void sortUpdate(List<? extends UpdateModule> modules);
	
}
