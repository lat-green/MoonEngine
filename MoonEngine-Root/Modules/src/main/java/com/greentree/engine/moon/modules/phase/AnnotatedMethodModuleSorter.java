package com.greentree.engine.moon.modules.phase;

import java.util.List;

import com.greentree.engine.moon.modules.AnnotationUtil;

public record AnnotatedMethodModuleSorter() implements MethodModuleSorter {
	
	public static MethodModuleSorter INSTANCE = new AnnotatedMethodModuleSorter();
	
	@Override
	public void sort(List<?> modules, String method) {
		AnnotationUtil.sort(modules, method);
	}
	
	
}
