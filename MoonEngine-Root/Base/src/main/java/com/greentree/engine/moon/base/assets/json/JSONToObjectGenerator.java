package com.greentree.engine.moon.base.assets.json;

import java.lang.reflect.Modifier;
import java.util.function.Function;

import com.google.gson.JsonElement;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.util.classes.ClassUtil;
import com.greentree.commons.util.classes.info.TypeInfo;

public class JSONToObjectGenerator implements Function<TypeInfo<?>, AssetSerializator<?>> {
	
	@Override
	public AssetSerializator<?> apply(TypeInfo<?> type) {
		if(ClassUtil.isExtends(JsonElement.class, type.toClass()))
			return null;
		if(Modifier.isAbstract(type.toClass().getModifiers()))
			return null;
		//		System.out.println(type);
		return new JSONToObjectSerializator<>(type);
		//		return null;
	}
	
}
