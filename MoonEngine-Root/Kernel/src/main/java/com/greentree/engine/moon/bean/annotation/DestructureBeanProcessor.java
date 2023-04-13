package com.greentree.engine.moon.bean.annotation;

import com.greentree.commons.util.classes.ClassUtil;
import com.greentree.engine.moon.bean.EngineBeanProcessor;
import com.greentree.engine.moon.bean.container.ConfigurableBeanContainer;
import com.greentree.engine.moon.module.EngineProperty;

@EngineBean
public class DestructureBeanProcessor
		implements EngineBeanProcessor {
	
	private ConfigurableBeanContainer ctx;
	
	@Override
	public Object process(Object bean) {
		if(bean instanceof EngineProperty p) {
			var cls = p.getClass();
			if(cls.isRecord()) {
				ClassUtil.getAllNotStaticFields(cls).stream().forEach(x -> {
					try {
						var v = ClassUtil.getField(bean, x);
						ctx.addBean(v);
						System.out.println(v);
					}catch(IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				});
			}
		}
		return bean;
	}
}
