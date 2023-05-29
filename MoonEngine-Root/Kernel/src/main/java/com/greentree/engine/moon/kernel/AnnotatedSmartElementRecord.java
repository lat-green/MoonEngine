package com.greentree.engine.moon.kernel;

import static com.greentree.engine.moon.kernel.AnnotationUtil.*;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public record AnnotatedSmartElementRecord(AnnotatedElement annotatedElement) implements AnnotatedSmartElement {
	
	private static Logger log = LogManager.getLogger(AnnotatedSmartElementRecord.class);
	
	public AnnotatedSmartElementRecord {
		//		if(annotatedElement instanceof Class<?> cls && cls.isAnnotation())
		//			throw new UnsupportedOperationException("annotatedElement: " + annotatedElement + " is Annotation");
	}
	
	@Override
	public Stream<SmartAnnotation<?>> getAnnotations() {
		return getNotRepeatableAnnotations(annotatedElement).flatMap(AnnotatedSmartElementRecord::getAll)
				.map(AnnotationUtil::toSmart);
	}
	
	private static Stream<Annotation> getAll(Annotation annotation) {
		var inherited = getNotRepeatableAnnotations(annotation.annotationType())
				.filter(AnnotationUtil::isAnnotationInherited).flatMap(x -> getAll(annotation, x));
		return Stream.concat(Stream.of(annotation), inherited);
	}
	
	private static Stream<Annotation> getAll(Annotation baseAnnotation, Annotation annotation) {
		annotation = makeAliases(baseAnnotation, annotation);
		var inherited = getNotRepeatableAnnotations(annotation.annotationType())
				.filter(AnnotationUtil::isAnnotationInherited)
				.flatMap(x -> getAll(baseAnnotation, x));
		return Stream.concat(Stream.of(annotation), inherited);
	}
	
	@Override
	public <T extends Annotation> Stream<SmartAnnotation<T>> getAnnotations(Class<T> annotationClass) {
		if(isAnnotationInherited(annotationClass))
			return AnnotatedSmartElement.super.getAnnotations(annotationClass);
		return Stream.of(annotatedElement.getAnnotationsByType(annotationClass)).map(AnnotationUtil::toSmart);
	}
	
	public static boolean isRepeatable(Class<? extends Annotation> annotationType) {
		Method method;
		try {
			method = annotationType.getMethod("value");
		}catch(NoSuchMethodException | SecurityException e) {
			return false;
		}
		var returnType = method.getReturnType();
		if(!returnType.isArray())
			return false;
		var repeatable = AnnotationUtil.getAnnotation(returnType.componentType(), Repeatable.class);
		if(repeatable.isEmpty())
			return false;
		return repeatable.get().value() == annotationType;
	}
	
	
	@SuppressWarnings("unchecked")
	private static <A extends Annotation> A makeAliases(Annotation baseAnnotation, A annotation) {
		if(hasReflectiveAlias(annotation))
			throw new UnsupportedOperationException("annotation: " + annotation);
		
		if(!getPath(baseAnnotation, annotation).anyMatch(x -> hasAlias(x, annotation)))
			return annotation;
		
		var cls = annotation.annotationType().getAnnotation(AnnotationInherited.class).value();
		
		Constructor<?> constructor;
		try {
			constructor = cls.getConstructor(Map.class);
		}catch(NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("Please create constructor(Map) in " + cls + " ", e);
		}
		
		var map = new HashMap<String, Object>();
		
		getPath(baseAnnotation, annotation).filter(x -> hasAlias(x, annotation)).forEach(aliasAnnotation -> {
			var aliasAnnotationType = aliasAnnotation.annotationType();
			for(var method : aliasAnnotationType.getMethods()) {
				var alias = AnnotationUtil.getAnnotation(method, AliasFor.class);
				if(alias.isPresent()) {
					final Object value;
					try {
						value = method.invoke(aliasAnnotation);
					}catch(IllegalAccessException | InvocationTargetException e) {
						throw new RuntimeException(e);
					}
					if(value != method.getDefaultValue()) {
						var methodName = method.getName();
						if(map.containsKey(methodName))
							throw new UnsupportedOperationException(
									"double alias on " + annotation + " in " + baseAnnotation);
						map.put(methodName, value);
					}
				}
			}
		});
		if(map.isEmpty())
			return annotation;
		var annotationType = annotation.annotationType();
		
		log.debug(() -> "new annatation " + cls.getSimpleName() + " " + toString(map));
		
		for(var method : annotationType.getMethods()) {
			var methodName = method.getName();
			if(!map.containsKey(methodName) && method.getParameterCount() == 0) {
				final Object value;
				try {
					value = method.invoke(annotation);
				}catch(IllegalAccessException | InvocationTargetException e) {
					throw new RuntimeException(e);
				}
				map.put(methodName, value);
			}
		}
		try {
			var result = (A) constructor.newInstance(map);
			return result;
		}catch(InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static Stream<Annotation> getNotRepeatableAnnotations(AnnotatedElement annotatedElement) {
		return Stream.of(annotatedElement.getAnnotations()).flatMap(annotation -> {
			var annotationType = annotation.annotationType();
			if(isRepeatable(annotationType))
				try {
					return Stream.of((Annotation[]) annotationType.getMethod("value").invoke(annotation));
				}catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException
						| SecurityException e) {
					e.printStackTrace();
				}
			return Stream.of(annotation);
		});
	}
	
	private static String toString(Map<? extends String, ? extends Object> map) {
		var stringMap = new HashMap<String, String>();
		for(var entry : map.entrySet()) {
			stringMap.put(entry.getKey(), toString(entry.getValue()));
		}
		return stringMap.toString();
	}
	
	private static String toString(Object value) {
		if(value instanceof Object[] arr)
			return Arrays.toString(arr);
		return value.toString();
	}
	
	private static Stream<Annotation> getNotRepeatableAnnotations(Object object) {
		return getNotRepeatableAnnotations(object.getClass());
	}
	
	private static Stream<Annotation> getNotRepeatableAnnotations(Annotation annotation) {
		return getNotRepeatableAnnotations(annotation.annotationType());
	}
	
	private static Stream<Annotation> getPath(Annotation begin, Annotation end) {
		if(begin == end)
			return Stream.of(begin);
		return getNotRepeatableAnnotations(begin).filter(AnnotationUtil::isAnnotationInherited)
				.map(x -> getPath(x, end)).filter(x -> x != null).findAny().map(x -> Stream.concat(Stream.of(begin), x))
				.get();
	}
	
	private static <T extends Annotation> Optional<T> getInheritedAnnotation(AnnotatedElement annotatedElement,
			Class<T> annotationType, Set<AnnotatedElement> checked) {
		checked.add(annotatedElement);
		var a = annotatedElement.getAnnotation(annotationType);
		if(a != null)
			return Optional.of(a);
		//		return getNotRepeatableAnnotations(annotationType).map(Annotation::annotationType)
		//				.filter(x -> !checked.contains(x))
		//				.map(x -> getInheritedAnnotation(x, annotationType, checked)).findAny().orElse(Optional.empty());
		
		getNotRepeatableAnnotations(annotatedElement).flatMap(baseAnnotation -> {
			var baseAnnotationType = baseAnnotation.annotationType();
			if(checked.contains(baseAnnotationType))
				return Stream.empty();
			return getInheritedAnnotations(annotatedElement, baseAnnotation, annotationType, checked);
		}).peek(x -> {
			System.out.println(x);
		});
		
		return Optional.empty();
	}
	
	private static <T extends Annotation> Stream<T> getInheritedAnnotations(AnnotatedElement annotatedElement,
			Annotation baseAnnotation, Class<T> annotationType, Set<AnnotatedElement> checked) {
		return Stream.empty();
	}
	
	private static boolean hasAlias(Annotation alias, Annotation annotation) {
		return Stream.of(alias.annotationType().getMethods())
				.flatMap(method -> AnnotationUtil.getAnnotations(method, AliasFor.class))
				.anyMatch(a -> a.annotation().equals(annotation.annotationType()));
	}
	
	private static <A extends Annotation> boolean hasReflectiveAlias(A annotation) {
		return Stream.of(annotation.annotationType().getMethods())
				.flatMap(method -> AnnotationUtil.getAnnotations(method, AliasFor.class))
				.anyMatch(a -> a.annotation() == Annotation.class);
	}
	
}
