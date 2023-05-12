package com.greentree.engine.moon.modules.scanner;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.greentree.engine.moon.modules.EngineModule;

@Component
public class SpringModuleDefenitionScanner implements ModuleDefenitionScanner {
	
	@Autowired
	ApplicationContext context;
	
	@Override
	public Stream<? extends ModuleDefenition> scan() {
		return context.getBeansOfType(EngineModule.class).values().stream().map(m -> new ObjectModuleDefenition(m));
	}
	
}
