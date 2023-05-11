package com.greentree.engine.moon.modules.scanner;

import java.util.stream.Stream;

import org.springframework.beans.BeansException;
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
		try {
			System.out.println(context.getBean(Class.forName("com.greentree.engine.moon.demo1.Main")));
		}catch(BeansException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(context.getBeansOfType(EngineModule.class));
		return context.getBeansOfType(EngineModule.class).values().stream().map(m -> new ObjectModuleDefenition(m));
	}
	
}
