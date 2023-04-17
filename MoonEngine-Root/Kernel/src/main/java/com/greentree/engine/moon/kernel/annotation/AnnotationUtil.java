package com.greentree.engine.moon.kernel.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class AnnotationUtil {
	
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
	
}
