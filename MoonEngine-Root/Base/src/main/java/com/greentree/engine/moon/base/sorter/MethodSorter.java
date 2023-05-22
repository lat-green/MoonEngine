package com.greentree.engine.moon.base.sorter;

import java.util.List;

import com.greentree.commons.graph.DirectedGraph;
import com.greentree.commons.graph.Graph;
import com.greentree.commons.graph.GraphUtil;
import com.greentree.commons.util.iterator.IteratorUtil;

public interface MethodSorter {
	
	<T> Graph<? extends T> buildDependencyGraph(List<? extends T> modules, String method);
	
	default <T> void sort(List<T> modules, String method) {
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
