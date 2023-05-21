package com.greentree.engine.moon.base.modules.sorter;

import java.util.List;

import com.greentree.commons.graph.Graph;
import com.greentree.engine.moon.base.modules.AnnotationUtil;
import com.greentree.engine.moon.modules.EngineModule;
public class AnnotatedMethodModuleSorter implements MethodModuleSorter {
	
	@Override
	public <T extends EngineModule> Graph<? extends T> buildDependencyGraph(List<? extends T> modules, String method) {
		return AnnotationUtil.getGraph(modules, method);
	}
	
	//	@Override
	//	public <T extends EngineModule> void sort(List<T> modules, String method) {
	//		AnnotationUtil.sort(modules, method);
	//	}
	
	
}
