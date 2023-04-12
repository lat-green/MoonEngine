package com.greentree.engine.moon.bean.module;

import java.util.Iterator;

import com.greentree.engine.moon.bean.EngineContext;
import com.greentree.engine.moon.bean.annotation.EngineBean;
import com.greentree.engine.moon.module.EngineProperties;
import com.greentree.engine.moon.module.EngineProperty;

@EngineBean
public class EngineContextEngineProperties implements EngineProperties {
	
	private EngineContext context;
	
	@Override
	public void add(EngineProperty property) {
		context.addBean(property);
	}
	
	@Override
	public <T extends EngineProperty> T get(Class<T> cls) {
		return context.getBean(cls).orElse(null);
	}
	
	@Override
	public boolean has(Class<? extends EngineProperty> cls) {
		return context.hasBean(cls);
	}
	
	@Override
	public Iterator<EngineProperty> iterator() {
		return context.getBeans(EngineProperty.class).iterator();
	}
	
}
