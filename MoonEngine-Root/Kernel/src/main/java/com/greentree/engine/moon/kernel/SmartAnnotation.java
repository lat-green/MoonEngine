package com.greentree.engine.moon.kernel;

import java.lang.annotation.Annotation;

public interface SmartAnnotation<A extends Annotation> {
	
	A annotation();
	
	default Class<? extends Annotation> annotationType() {
		return annotation().annotationType();
	}
	
}
