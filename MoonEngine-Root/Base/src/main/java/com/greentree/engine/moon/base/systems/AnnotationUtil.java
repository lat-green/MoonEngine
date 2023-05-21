package com.greentree.engine.moon.base.systems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.greentree.commons.graph.DirectedGraph;
import com.greentree.commons.util.collection.FunctionAutoGenerateMap;
import com.greentree.commons.util.iterator.IteratorUtil;

@Deprecated
public class AnnotationUtil {
	
	public static final String INIT = "init";
	public static final String UPDATE = "update";
	public static final String DESTROY = "destroy";
	
	public static <T> void sortInit(List<T> list) {
		sort(list, INIT);
	}
	
	public static <T> void sortUpdate(List<T> list) {
		sort(list, UPDATE);
	}
	
	public static <T> void sortDestroy(List<T> list) {
		sort(list, DESTROY);
	}
	
	public static <T> void sort(List<T> list, String method) {
		final var graph = inverse(getGraph(list, method));
		
		final var cycle = graph.getCycleFinder().getCycles();
		if(!IteratorUtil.isEmpty(cycle))
			throw new IllegalArgumentException(list + " " + method + " has cycle " + cycle);
		
		final var buffer = new LinkedList<>(list);
		list.clear();
		while(!buffer.isEmpty())
			tryAdd(buffer.peek(), buffer, list, graph);
	}
	
	private static <T> void tryAdd(T e, LinkedList<T> buffer, Collection<? super T> dest,
			DirectedGraph<T> graph) {
		buffer.remove(e);
		for(var to : graph.getJoints(e)) {
			final var index = buffer.indexOf(to);
			if(index != -1)
				tryAdd(to, buffer, dest, graph);
		}
		dest.add(e);
	}
	
	private static <T> DirectedGraph<T> inverse(DirectedGraph<T> graph) {
		final var result = new DirectedGraph<T>();
		
		for(var v : graph)
			result.add(v);
		
		for(var v : graph)
			for(var to : graph.getJoints(v))
				result.add(to, v);
			
		return result;
	}
	
	public static <T> DirectedGraph<T> getGraph(Iterable<T> iterable, String method) {
		final var result = new DirectedGraph<T>();
		
		for(var v : iterable)
			result.add(v);
		
		final var create = new FunctionAutoGenerateMap<Class<?>, Collection<T>>(
				()->new ArrayList<>());
		final var write = new FunctionAutoGenerateMap<Class<?>, Collection<T>>(
				()->new ArrayList<>());
		final var read = new FunctionAutoGenerateMap<Class<?>, Collection<T>>(
				()->new ArrayList<>());
		final var destroy = new FunctionAutoGenerateMap<Class<?>, Collection<T>>(
				()->new ArrayList<>());
		
		for(var v : iterable) {
			UseStage.CREATE.get(v, method).forEach(x -> {
				create.get(x).add(v);
			});
			UseStage.WRITE.get(v, method).forEach(x -> {
				write.get(x).add(v);
			});
			UseStage.READ.get(v, method).forEach(x -> {
				read.get(x).add(v);
			});
			UseStage.DESTROY.get(v, method).forEach(x -> {
				destroy.get(x).add(v);
			});
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
			for(var a : entry.getValue()) {
				for(var b : destroy.get(c))
					result.add(a, b);
			}
		}
		return result;
	}
	
}
