package com.greentree.engine.moon.base.sorter

import com.greentree.commons.graph.DirectedGraph
import com.greentree.commons.graph.Graph

interface MethodSorter {

	fun <T> sort(modules: MutableList<T>, method: String) {
		val graph = buildDependencyGraph<T>(modules, method) as DirectedGraph<T>
		val cycle = graph.cycleFinder.cycles
		require(cycle.isEmpty()) { "$method has cycle ${cycle.first()}" }
		for(v in modules) graph.add(v)
		modules.clear()
		graph.getTopologicalSort(modules)
	}

	fun <T> buildDependencyGraph(modules: List<T>, method: String): Graph<out T>
}
