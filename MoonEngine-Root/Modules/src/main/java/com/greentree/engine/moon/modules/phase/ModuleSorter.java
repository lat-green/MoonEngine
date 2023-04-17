package com.greentree.engine.moon.modules.phase;

import java.util.List;

public interface ModuleSorter<T> {
	
	void sort(List<? extends T> modules);
	
}
