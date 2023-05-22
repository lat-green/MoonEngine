package com.greentree.engine.moon.kernel;

import java.lang.annotation.Annotation;

public record SmartAnnotationRecord<A extends Annotation>(A annotation) implements SmartAnnotation<A> {
	
}
