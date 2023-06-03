package com.greentree.engine.moon.base.assets.json;

import com.google.gson.JsonElement;
import com.greentree.commons.reflection.ClassUtil;
import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;

import java.lang.reflect.Modifier;
import java.util.function.Function;

public class JSONToObjectGenerator implements Function<TypeInfo<?>, AssetSerializator<?>> {

    @Override
    public AssetSerializator<?> apply(TypeInfo<?> type) {
        if (ClassUtil.isExtends(JsonElement.class, type.toClass()))
            return null;
        if (Modifier.isAbstract(type.toClass().getModifiers()))
            return null;
        //		System.out.println(type);
        return new JSONToObjectSerializator<>(type);
        //		return null;
    }

}
