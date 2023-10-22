package com.greentree.engine.moon.kernel;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Optional;
import java.util.stream.Stream;

public class AnnotationUtil {
	public static <A extends Annotation> SmartAnnotation<A> toSmart(A annotation) {
		return new SmartAnnotationRecord<>(annotation);
	}
	
	public static AnnotatedSmartElement toSmart(AnnotatedElement annotatedElement) {
		return new AnnotatedSmartElementRecord(annotatedElement);
	}
	
	public static <T extends Annotation> Optional<T> getAnnotation(Annotation annotation, Class<T> annotationType) {
		return getAnnotation(annotation.annotationType(), annotationType);
	}
	public static <T extends Annotation> Optional<T> getAnnotation(Object object, Class<T> annotationType) {
		return getAnnotation(object.getClass(), annotationType);
	}
	public static <T extends Annotation> Optional<T> getAnnotation(AnnotatedElement annotatedElement,
			Class<T> annotationType) {
		return toSmart(annotatedElement).getAnnotation(annotationType).map(SmartAnnotation::annotation);
	}
	
	public static <T extends Annotation> Stream<T> getAnnotations(Object object, Class<T> annotationType) {
		return getAnnotations(object.getClass(), annotationType);
	}
	
	public static <T extends Annotation> Stream<T> getAnnotations(Annotation annotation, Class<T> annotationType) {
		return getAnnotations(annotation.annotationType(), annotationType);
	}
	
	public static <T extends Annotation> Stream<T> getAnnotations(AnnotatedElement annotatedElement,
			Class<T> annotationType) {
		return toSmart(annotatedElement).getAnnotations(annotationType).map(SmartAnnotation::annotation);
	}
	
	public static boolean hasAnnotation(Annotation annotation, Class<? extends Annotation> annotationType) {
		return hasAnnotation(annotation.annotationType(), annotationType);
	}
	
	public static boolean hasAnnotation(Object object, Class<? extends Annotation> annotationType) {
		return hasAnnotation(object.getClass(), annotationType);
	}
	
	public static boolean hasAnnotation(AnnotatedElement annotatedElement, Class<? extends Annotation> annotationType) {
		return toSmart(annotatedElement).isAnnotationPresent(annotationType);
	}
	
	public static boolean isAnnotationInherited(Annotation annotation) {
		return isAnnotationInherited(annotation.annotationType());
	}
	
	public static boolean isAnnotationInherited(Class<? extends Annotation> annotationType) {
		if(annotationType == AnnotationInherited.class)
			return false;
		return hasAnnotation(annotationType, AnnotationInherited.class);
	}
	
}