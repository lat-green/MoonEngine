package com.greentree.engine.moon.base.sorter;

import com.greentree.commons.graph.DirectedGraph;
import com.greentree.commons.graph.Graph;
import com.greentree.commons.util.iterator.IteratorUtil;

import java.util.List;

public interface MethodSorter {

    default <T> void sort(List<T> modules, String method) {
        @SuppressWarnings("unchecked")
        var graph = (DirectedGraph<T>) buildDependencyGraph(modules, method);
        final var cycle = graph.getCycleFinder().getCycles();
        if (!IteratorUtil.isEmpty(cycle))
            throw new IllegalArgumentException(modules + " " + method + " has cycle " + cycle);
        for (var v : modules)
            graph.add(v);
        modules.clear();
        graph.getTopologicalSort(modules);
    }

    <T> Graph<? extends T> buildDependencyGraph(List<? extends T> modules, String method);

}
