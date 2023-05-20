package com.greentree.engine.moon.modules.phase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.greentree.commons.graph.DirectedGraph;
import com.greentree.commons.graph.Graph;
import com.greentree.commons.util.collection.FunctionAutoGenerateMap;
import com.greentree.engine.moon.modules.EngineModule;

public record OnCWRDMethodModuleSorter(CWRDMethodModuleInfo info) implements MethodModuleSorter {
	
	@Override
	public <T extends EngineModule> Graph<? extends T> buildDependencyGraph(List<? extends T> modules, String method) {
		final var result = new DirectedGraph<T>();
		
		for(var v : modules)
			result.add(v);
		
		final var create = new FunctionAutoGenerateMap<Class<?>, Collection<T>>(() -> new ArrayList<>());
		final var write = new FunctionAutoGenerateMap<Class<?>, Collection<T>>(() -> new ArrayList<>());
		final var read = new FunctionAutoGenerateMap<Class<?>, Collection<T>>(() -> new ArrayList<>());
		final var destroy = new FunctionAutoGenerateMap<Class<?>, Collection<T>>(() -> new ArrayList<>());
		
		for(var v : modules) {
			for(var c : info.getCreate(v, method))
				create.get(c).add(v);
			for(var c : info.getWrite(v, method))
				write.get(c).add(v);
			for(var c : info.getRead(v, method))
				read.get(c).add(v);
			for(var c : info.getDestroy(v, method))
				destroy.get(c).add(v);
		}
		
		for(var entry : create.entrySet()) {
			final var c = entry.getKey();
			for(var a : entry.getValue()) {
				for(var b : write.get(c))
					result.add(a, b);
				for(var b : read.get(c))
					result.add(a, b);
				for(var b : destroy.get(c))
					result.add(a, b);
			}
		}
		for(var entry : write.entrySet()) {
			final var c = entry.getKey();
			for(var a : entry.getValue()) {
				for(var b : read.get(c))
					result.add(a, b);
				for(var b : destroy.get(c))
					result.add(a, b);
			}
		}
		for(var entry : read.entrySet()) {
			final var c = entry.getKey();
			for(var a : entry.getValue())
				for(var b : destroy.get(c))
					result.add(a, b);
		}
		return result;
	}
	
}
