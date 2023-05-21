package com.greentree.engine.moon.base.modules.info;

import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.modules.EngineModule;

public record MergeCWRDMethodModuleInfo(Iterable<CWRDMethodModuleInfo> infos) implements CWRDMethodModuleInfo {
	
	
	public MergeCWRDMethodModuleInfo(CWRDMethodModuleInfo... infos) {
		this(IteratorUtil.iterable(infos));
	}
	
	@Override
	public Iterable<Class<?>> getCreate(EngineModule module, String method) {
		return IteratorUtil.union(IteratorUtil.map(infos, x -> x.getCreate(module, method)));
	}
	
	@Override
	public Iterable<Class<?>> getWrite(EngineModule module, String method) {
		return IteratorUtil.union(IteratorUtil.map(infos, x -> x.getWrite(module, method)));
	}
	
	@Override
	public Iterable<Class<?>> getRead(EngineModule module, String method) {
		return IteratorUtil.union(IteratorUtil.map(infos, x -> x.getRead(module, method)));
	}
	
	@Override
	public Iterable<Class<?>> getDestroy(EngineModule module, String method) {
		return IteratorUtil.union(IteratorUtil.map(infos, x -> x.getDestroy(module, method)));
	}
	
}
