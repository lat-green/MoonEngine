package com.greentree.engine.moon.modules.phase;

import java.util.List;

import com.greentree.engine.moon.kernel.annotation.EngineBean;
import com.greentree.engine.moon.modules.AnnotationUtil;

@EngineBean
public class AnnotatedMethodModuleSorter implements MethodModuleSorter {
	
	@Override
	public void sort(List<?> modules, String method) {
		AnnotationUtil.sort(modules, method);
	}
	
	
}
