package com.greentree.engine.moon.modules.phase;

import java.util.List;

import com.greentree.engine.moon.modules.AnnotationUtil;

public record AnnotatedMethodModuleSorter<T>(String methodName) implements ModuleSorter<T> {
	
	@Override
	public void sort(List<? extends T> modules) {
		AnnotationUtil.sort(modules, methodName);
		
	}
	
	
}
