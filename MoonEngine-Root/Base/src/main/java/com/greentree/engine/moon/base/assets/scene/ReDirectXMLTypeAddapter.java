package com.greentree.engine.moon.base.assets.scene;

import java.util.Objects;

import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;
import com.greentree.commons.xml.XMLElement;

public record ReDirectXMLTypeAddapter(Class<?> in, Class<?> to) implements XMLTypeAddapter {
	
	public ReDirectXMLTypeAddapter {
		Objects.requireNonNull(in);
		Objects.requireNonNull(to);
		if(in.equals(to))
			throw new IllegalArgumentException("in == to " + in);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Constructor<T> newInstance(Context context, TypeInfo<T> type, XMLElement element) {
		if(type.toClass() == in) {
			final var to_type = TypeInfoBuilder.getTypeInfo(to, type.getTypeArguments());
			return (Constructor<T>) context.newInstance(to_type, element);
		}
		return null;
	}
	
}
