package com.greentree.engine.moon.base.modules.sorter;

import java.util.List;

import com.greentree.commons.graph.DirectedGraph;
import com.greentree.commons.graph.Graph;
import com.greentree.commons.graph.GraphUtil;
import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.modules.EngineModule;

public interface MethodModuleSorter {
	
	<T extends EngineModule> Graph<? extends T> buildDependencyGraph(List<? extends T> modules, String method);
	
	default <T extends EngineModule> void sort(List<T> modules, String method) {
		@SuppressWarnings("unchecked")
		var graph = (DirectedGraph<T>) GraphUtil.inverse(buildDependencyGraph(modules, method));
		
		final var cycle = graph.getCycleFinder().getCycles();
		if(!IteratorUtil.isEmpty(cycle))
			throw new IllegalArgumentException(modules + " " + method + " has cycle " + cycle);
		
		for(var v : modules)
			graph.add(v);
		modules.clear();
		graph.getTopologicalSort(modules);
	}
	
}
