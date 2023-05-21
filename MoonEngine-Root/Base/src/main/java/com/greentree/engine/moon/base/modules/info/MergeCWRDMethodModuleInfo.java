package com.greentree.engine.moon.base.modules.info;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import com.greentree.engine.moon.modules.EngineModule;

public record MergeCWRDMethodModuleInfo(Collection<CWRDMethodModuleInfo> infos) implements CWRDMethodModuleInfo {
	
	
	public MergeCWRDMethodModuleInfo(CWRDMethodModuleInfo... infos) {
		this(List.of(infos));
	}
	
	@Override
	public Stream<Class<?>> getCreate(EngineModule module, String method) {
		return infos.stream().flatMap(x -> x.getCreate(module, method));
	}
	
	@Override
	public Stream<Class<?>> getWrite(EngineModule module, String method) {
		return infos.stream().flatMap(x -> x.getWrite(module, method));
	}
	
	@Override
	public Stream<Class<?>> getRead(EngineModule module, String method) {
		return infos.stream().flatMap(x -> x.getRead(module, method));
	}
	
	@Override
	public Stream<Class<?>> getDestroy(EngineModule module, String method) {
		return infos.stream().flatMap(x -> x.getDestroy(module, method));
	}
	
}
