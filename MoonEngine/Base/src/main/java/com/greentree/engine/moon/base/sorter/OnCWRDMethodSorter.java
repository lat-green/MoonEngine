package com.greentree.engine.moon.base.sorter;

import com.greentree.commons.graph.DirectedGraph;
import com.greentree.commons.graph.Graph;
import com.greentree.commons.util.collection.FunctionAutoGenerateMap;
import com.greentree.engine.moon.base.info.CWRDMethodInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public record OnCWRDMethodSorter(CWRDMethodInfo info) implements MethodSorter {

    @Override
    public <T> Graph<? extends T> buildDependencyGraph(List<? extends T> modules, String method) {
        final var result = new DirectedGraph<T>();
        for (var v : modules)
            result.add(v);
        final var create = new FunctionAutoGenerateMap<Class<?>, Collection<T>>(() -> new ArrayList<>());
        final var write = new FunctionAutoGenerateMap<Class<?>, Collection<T>>(() -> new ArrayList<>());
        final var read = new FunctionAutoGenerateMap<Class<?>, Collection<T>>(() -> new ArrayList<>());
        final var destroy = new FunctionAutoGenerateMap<Class<?>, Collection<T>>(() -> new ArrayList<>());
        final var postDestroy = new FunctionAutoGenerateMap<Class<?>, Collection<T>>(() -> new ArrayList<>());
        for (var v : modules) {
            info.getCreate(v, method).forEach(c -> create.get(c).add(v));
            info.getWrite(v, method).forEach(c -> write.get(c).add(v));
            info.getRead(v, method).forEach(c -> read.get(c).add(v));
            info.getDestroy(v, method).forEach(c -> destroy.get(c).add(v));
        }
        for (var entry : create.entrySet()) {
            final var c = entry.getKey();
            for (var a : entry.getValue()) {
                for (var b : write.get(c))
                    result.add(a, b);
                for (var b : read.get(c))
                    result.add(a, b);
                for (var b : destroy.get(c))
                    result.add(a, b);
                for (var b : postDestroy.get(c))
                    result.add(a, b);
            }
        }
        for (var entry : write.entrySet()) {
            final var c = entry.getKey();
            for (var a : entry.getValue()) {
                for (var b : read.get(c))
                    result.add(a, b);
                for (var b : destroy.get(c))
                    result.add(a, b);
                for (var b : postDestroy.get(c))
                    result.add(a, b);
            }
        }
        for (var entry : read.entrySet()) {
            final var c = entry.getKey();
            for (var a : entry.getValue()) {
                for (var b : destroy.get(c))
                    result.add(a, b);
                for (var b : postDestroy.get(c))
                    result.add(a, b);
            }
        }
        for (var entry : destroy.entrySet()) {
            final var c = entry.getKey();
            for (var a : entry.getValue()) {
                for (var b : postDestroy.get(c))
                    result.add(a, b);
            }
        }
        return result;
    }

}
