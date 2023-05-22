package com.greentree.engine.moon.base.info;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public record MergeCWRDMethodInfo(Collection<CWRDMethodInfo> infos) implements CWRDMethodInfo {
	
	public MergeCWRDMethodInfo(CWRDMethodInfo... infos) {
		this(List.of(infos));
	}
	
	@Override
	public Stream<Class<?>> getCreate(Object object, String method) {
		return infos.stream().flatMap(x -> x.getCreate(object, method));
	}
	
	@Override
	public Stream<Class<?>> getWrite(Object object, String method) {
		return infos.stream().flatMap(x -> x.getWrite(object, method));
	}
	
	@Override
	public Stream<Class<?>> getRead(Object object, String method) {
		return infos.stream().flatMap(x -> x.getRead(object, method));
	}
	
	@Override
	public Stream<Class<?>> getDestroy(Object object, String method) {
		return infos.stream().flatMap(x -> x.getDestroy(object, method));
	}
	
}
