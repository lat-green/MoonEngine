package com.greentree.engine.moon.bean.module;

import java.util.Iterator;

import com.greentree.engine.moon.bean.annotation.EngineBean;
import com.greentree.engine.moon.bean.container.ConfigurableBeanContainer;
import com.greentree.engine.moon.module.EngineProperties;
import com.greentree.engine.moon.module.EngineProperty;

@EngineBean
public class BeanEngineProperties implements EngineProperties {
	
	private ConfigurableBeanContainer ctx;
	
	@Override
	public Iterator<EngineProperty> iterator() {
		return ctx.getBeans(EngineProperty.class).iterator();
	}
	
	@Override
	public void add(EngineProperty property) {
		ctx.addBean(property);
	}
	
	@Override
	public <T extends EngineProperty> T get(Class<T> cls) {
		return ctx.getBean(cls).orElse(null);
	}
	
	@Override
	public boolean has(Class<? extends EngineProperty> cls) {
		return ctx.hasBean(cls);
	}
	
}
