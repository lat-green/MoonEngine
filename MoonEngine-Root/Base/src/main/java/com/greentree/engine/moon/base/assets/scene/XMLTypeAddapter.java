package com.greentree.engine.moon.base.assets.scene;

import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;
import com.greentree.commons.xml.XMLElement;

public interface XMLTypeAddapter {
	
	
	public interface Context {
		
		default <T> T build(Class<T> cls, XMLElement element) {
			final var type = TypeInfoBuilder.getTypeInfo(cls);
			return build(type, element);
		}
		
		default <T> T build(TypeInfo<T> type, XMLElement element) {
			try(final var c = newInstance(type, element)) {
				return c.value();
			}
		}
		
		default <T> Constructor<T> newInstance(Class<T> cls, XMLElement element) {
			final var type = TypeInfoBuilder.getTypeInfo(cls);
			return newInstance(type, element);
		}
		
		<T> Constructor<T> newInstance(TypeInfo<T> type, XMLElement element);
		
	}
	
	default <T> Constructor<T> newInstance(Context context, Class<T> cls, XMLElement element) {
		final var type = TypeInfoBuilder.getTypeInfo(cls);
		return newInstance(context, type, element);
	}
	
	<T> Constructor<T> newInstance(Context context, TypeInfo<T> type, XMLElement element);
	
}
