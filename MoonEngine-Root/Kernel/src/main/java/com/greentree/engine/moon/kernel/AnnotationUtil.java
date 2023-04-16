package com.greentree.engine.moon.kernel;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Stream;

import com.greentree.commons.graph.DirectedGraph;
import com.greentree.engine.moon.kernel.annotation.AnnotationInherited;

public class AnnotationUtil {
	
	public static final String INIT = "init";
	public static final String UPDATE = "update";
	public static final String DESTROY = "destroy";
	
	public static <A extends Annotation> A get(Object a, String method, Class<A> annotationClass) {
		final var m = getMethod(a.getClass(), method);
		if(m == null)
			return null;
		return m.getAnnotation(annotationClass);
	}
	
	public static <T extends Annotation> T getAnnotation(AnnotatedElement cls, Class<T> annotationType) {
		var a = cls.getAnnotation(annotationType);
		if(a != null)
			return a;
		if(isAnnotationInherited(annotationType))
			return getInheritedAnnotation(cls, annotationType, new HashSet<>());
		return null;
	}
	
	public static <T extends Annotation> Stream<T> getAnnotations(AnnotatedElement cls, Class<T> annotationType) {
		var a = Stream.of(cls.getAnnotationsByType(annotationType));
		if(isAnnotationInherited(annotationType))
			return Stream.concat(a, getInheritedAnnotations(cls, annotationType, new HashSet<>()));
		return a;
	}
	
	public static boolean hasAnnotation(AnnotatedElement cls, Class<? extends Annotation> annotationType) {
		return getAnnotation(cls, annotationType) != null;
	}
	
	public static boolean isAnnotationInherited(Class<? extends Annotation> annotationType) {
		if(annotationType == AnnotationInherited.class)
			return false;
		return hasAnnotation(annotationType, AnnotationInherited.class);
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
	
	private static <T extends Annotation> T getInheritedAnnotation(AnnotatedElement cls, Class<T> annotationType,
			Set<AnnotatedElement> checked) {
		checked.add(cls);
		var a = cls.getAnnotation(annotationType);
		if(a != null)
			return a;
		var opt = Stream.of(cls.getAnnotations()).map(Annotation::annotationType).filter(x -> !checked.contains(x))
				.map(x -> getInheritedAnnotation(x, annotationType, checked)).filter(x -> x != null).findAny();
		return opt.orElse(null);
	}
	
	private static <T extends Annotation> Stream<T> getInheritedAnnotations(AnnotatedElement cls,
			Class<T> annotationType,
			Set<AnnotatedElement> checked) {
		checked.add(cls);
		var a = Stream.of(cls.getAnnotationsByType(annotationType));
		var opt = Stream.of(cls.getAnnotations()).map(Annotation::annotationType)
				.filter(x -> !checked.contains(x))
				.map(x -> getInheritedAnnotation(x, annotationType, checked)).filter(x -> x != null);
		return Stream.concat(a, opt);
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
