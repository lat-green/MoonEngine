package com.greentree.engine.moon.base.modules.sorter;

import java.util.List;

import com.greentree.commons.graph.DirectedGraph;
import com.greentree.commons.graph.Graph;
import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.modules.EngineModule;

public record MergeModuleSorter(Iterable<MethodModuleSorter> sorters) implements MethodModuleSorter {
	
	
	public MergeModuleSorter(MethodModuleSorter... sorters) {
		this(IteratorUtil.iterable(sorters));
	}
	
	@Override
	public <T extends EngineModule> Graph<? extends T> buildDependencyGraph(List<? extends T> modules, String method) {
		var result = new DirectedGraph<T>();
		for(var sorter : sorters)
			result.addAll(sorter.buildDependencyGraph(modules, method));
		return result;
	}
	
}
