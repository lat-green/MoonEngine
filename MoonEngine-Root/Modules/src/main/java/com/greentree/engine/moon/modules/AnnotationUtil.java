package com.greentree.engine.moon.modules;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.greentree.commons.graph.DirectedGraph;
import com.greentree.commons.util.collection.FunctionAutoGenerateMap;
import com.greentree.commons.util.iterator.IteratorUtil;

public class AnnotationUtil {
	
	public static final String INIT = "init";
	public static final String UPDATE = "update";
	public static final String DESTROY = "destroy";
	
	public static void check(Object a, String method) {
		final var aCreate = getCreate(a, method);
		final var aWrite = getWrite(a, method);
		final var aRead = getRead(a, method);
		final var aDestroy = getDestroy(a, method);
		check(aCreate, aWrite, aRead, aDestroy);
	}
	
	public static <A extends Annotation> A get(Object a, String method, Class<A> annotationClass) {
		final var m = getMethod(a.getClass(), method);
		if(m == null)
			return null;
		return m.getAnnotation(annotationClass);
	}
	
	public static Iterable<Class<?>> getCreate(Object a, String method) {
		final var an = get(a, method, CreateProperty.class);
		if(an == null)
			return IteratorUtil.empty();
		return IteratorUtil.iterable(an.value());
	}
	
	public static Iterable<Class<?>> getDestroy(Object a, String method) {
		final var an = get(a, method, DestroyProperty.class);
		if(an == null)
			return IteratorUtil.empty();
		return IteratorUtil.iterable(an.value());
	}
	
	public static <T> DirectedGraph<T> getGraph(Iterable<T> iterable, String method) {
		final var result = new DirectedGraph<T>();
		
		for(var v : iterable)
			result.add(v);
		
		final var create = new FunctionAutoGenerateMap<Class<?>, Collection<T>>(() -> new ArrayList<>());
		final var write = new FunctionAutoGenerateMap<Class<?>, Collection<T>>(() -> new ArrayList<>());
		final var read = new FunctionAutoGenerateMap<Class<?>, Collection<T>>(() -> new ArrayList<>());
		final var destroy = new FunctionAutoGenerateMap<Class<?>, Collection<T>>(() -> new ArrayList<>());
		
		for(var v : iterable) {
			for(var c : getCreate(v, method))
				create.get(c).add(v);
			for(var c : getWrite(v, method))
				write.get(c).add(v);
			for(var c : getRead(v, method))
				read.get(c).add(v);
			for(var c : getDestroy(v, method))
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
	
	public static Iterable<Class<?>> getRead(Object a, String method) {
		final var an = get(a, method, ReadProperty.class);
		if(an == null)
			return IteratorUtil.empty();
		return IteratorUtil.iterable(an.value());
	}
	
	public static Iterable<Class<?>> getWrite(Object a, String method) {
		final var an = get(a, method, WriteProperty.class);
		if(an == null)
			return IteratorUtil.empty();
		return IteratorUtil.iterable(an.value());
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
	
	public static <T> void sortDestroy(List<T> list) {
		sort(list, DESTROY);
	}
	
	public static <T> void sortInit(List<T> list) {
		sort(list, INIT);
	}
	
	public static <T> void sortUpdate(List<T> list) {
		sort(list, UPDATE);
	}
	
	@SafeVarargs
	private static void check(Iterable<Class<?>>... s) {
		for(var a : s)
			for(var b : s) {
				if(a == b)
					break;
				final var c = cross(a, b);
				if(c.isEmpty())
					continue;
				throw new IllegalArgumentException("" + c);
			}
	}
	
	private static <T> boolean contains(Iterable<? extends T> iterable, T e) {
		if(e == null) {
			for(var ie : iterable)
				if(ie == null)
					return true;
		}else
			for(var ie : iterable)
				if(e.equals(ie))
					return true;
		return false;
	}
	
	private static <T> Collection<T> cross(Iterable<? extends T> a, Iterable<? extends T> b) {
		final var res = new ArrayList<T>();
		for(var e : a)
			if(contains(b, e))
				res.add(e);
		return res;
	}
	private static Method getMethod(Class<?> cls, String method) {
		for(var m : cls.getMethods())
			if(m.getName().equals(method))
				return m;
		if(cls != Object.class)
			return getMethod(cls.getSuperclass(), method);
		return null;
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
	
}
