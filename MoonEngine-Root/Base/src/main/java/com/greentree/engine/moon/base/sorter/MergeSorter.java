package com.greentree.engine.moon.base.sorter;

import java.util.List;

import com.greentree.commons.graph.DirectedGraph;
import com.greentree.commons.graph.Graph;
import com.greentree.commons.util.iterator.IteratorUtil;

public record MergeSorter(Iterable<MethodSorter> sorters) implements MethodSorter {
	public MergeSorter(MethodSorter... sorters) {
		this(IteratorUtil.iterable(sorters));
	}
	
	@Override
	public <T> Graph<? extends T> buildDependencyGraph(List<? extends T> modules, String method) {
		var result = new DirectedGraph<T>();
		for(var sorter : sorters)
			result.addAll(sorter.buildDependencyGraph(modules, method));
		return result;
	}
	
}
