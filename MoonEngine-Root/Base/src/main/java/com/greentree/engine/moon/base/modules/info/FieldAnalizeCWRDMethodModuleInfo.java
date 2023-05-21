package com.greentree.engine.moon.base.modules.info;

import java.util.stream.Stream;

import com.greentree.engine.moon.modules.EngineModule;

public record FieldAnalizeCWRDMethodModuleInfo() implements CWRDMethodModuleInfo {
	
	@Override
	public Stream<Class<?>> getCreate(EngineModule module, String method) {
		return Stream.empty();
	}
	
	@Override
	public Stream<Class<?>> getWrite(EngineModule module, String method) {
		return Stream.empty();
	}
	
	@Override
	public Stream<Class<?>> getRead(EngineModule module, String method) {
		//		if(!"launch".equals(method))
		//			return IteratorUtil.empty();
		//		var cls = AnnotationUtil.getRead(module, method);
		//		var c = getCandidateReadProprty(module);
		//		System.out.println(module + " " + method);
		//		System.out.println("use on Annatation:");
		//		for(var e : cls)
		//			System.out.println("\t" + e);
		//		System.out.println("fields:");
		//		for(var e : c)
		//			System.out.println("\t" + e);
		//		System.out.println();
		return Stream.empty();
	}
	
	//	private Iterable<Field> getCandidateReadProprty(EngineModule module) {
	//		var cls = module.getClass();
	//		return IteratorUtil.iterable(result -> {
	//			for(var c : ClassUtil.getAllNotStaticFields(cls))
	//				if(properties.isPropertyData(c.getType()))
	//					result.accept(c);
	//		});
	//	}
	
	@Override
	public Stream<Class<?>> getDestroy(EngineModule module, String method) {
		return Stream.empty();
	}
	
}
