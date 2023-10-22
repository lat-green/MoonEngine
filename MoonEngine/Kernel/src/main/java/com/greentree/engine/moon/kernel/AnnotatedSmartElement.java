package com.greentree.engine.moon.kernel;

import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.stream.Stream;

public interface AnnotatedSmartElement {
	
	default boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
		return getAnnotation(annotationClass).isPresent();
	}
	
	default <T extends Annotation> Optional<SmartAnnotation<T>> getAnnotation(Class<T> annotationClass) {
		return getAnnotations(annotationClass).findAny();
	}
	
	@SuppressWarnings("unchecked")
	default <T extends Annotation> Stream<SmartAnnotation<T>> getAnnotations(Class<T> annotationClass) {
		return getAnnotations().filter(x -> annotationClass == x.annotationType()).map(x -> (SmartAnnotation<T>) x);
	}
	
	Stream<SmartAnnotation<?>> getAnnotations();
	
}
