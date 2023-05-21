package com.greentree.engine.moon.kernel;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class AnnotationUtil {
	
	public static <A extends Annotation> Optional<A> get(Object object, String method, Class<A> annotationClass) {
		final var m = getMethod(object.getClass(), method);
		if(m == null)
			return Optional.empty();
		return Optional.ofNullable(m.getAnnotation(annotationClass));
	}
	
	public static <T extends Annotation> Optional<T> getAnnotation(AnnotatedElement annotatedElement,
			Class<T> annotationType) {
		var a = annotatedElement.getAnnotation(annotationType);
		if(a != null)
			return Optional.of(a);
		if(isAnnotationInherited(annotationType))
			return getInheritedAnnotation(annotatedElement, annotationType, new HashSet<>());
		return Optional.empty();
	}
	
	public static <T extends Annotation> Stream<T> getAnnotations(AnnotatedElement annotatedElement,
			Class<T> annotationType) {
		var a = Stream.of(annotatedElement.getAnnotationsByType(annotationType));
		if(isAnnotationInherited(annotationType))
			return Stream.concat(a, getInheritedAnnotations(annotatedElement, annotationType, new HashSet<>()));
		return a;
	}
	
	public static boolean hasAnnotation(AnnotatedElement cls, Class<? extends Annotation> annotationType) {
		return getAnnotation(cls, annotationType).isPresent();
	}
	
	public static boolean isAnnotationInherited(Class<? extends Annotation> annotationType) {
		if(annotationType == AnnotationInherited.class)
			return false;
		return hasAnnotation(annotationType, AnnotationInherited.class);
	}
	
	private static <T extends Annotation> Optional<T> getInheritedAnnotation(AnnotatedElement cls,
			Class<T> annotationType,
			Set<AnnotatedElement> checked) {
		checked.add(cls);
		var a = cls.getAnnotation(annotationType);
		if(a != null)
			return Optional.of(a);
		return Stream.of(cls.getAnnotations()).map(Annotation::annotationType).filter(x -> !checked.contains(x))
				.map(x -> getInheritedAnnotation(x, annotationType, checked)).findAny().orElse(Optional.empty());
	}
	
	private static <T extends Annotation> Stream<T> getInheritedAnnotations(AnnotatedElement cls,
			Class<T> annotationType, Set<AnnotatedElement> checked) {
		checked.add(cls);
		var a = Stream.of(cls.getAnnotationsByType(annotationType));
		var opt = Stream.of(cls.getAnnotations()).map(Annotation::annotationType).filter(x -> !checked.contains(x))
				.map(x -> getInheritedAnnotation(x, annotationType, checked)).filter(Optional::isPresent)
				.map(Optional::get);
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